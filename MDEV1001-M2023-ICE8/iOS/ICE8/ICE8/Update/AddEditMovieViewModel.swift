//
//  AddEditMovieViewModel.swift
//  MDEV1001-M2023-UIForLocalDataAssignment
//
//  Created by Abhijit Singh on 09/06/23.
//  Copyright © 2023 Abhijit Singh. All rights reserved.
//

import UIKit

protocol AddEditMovieListener: AnyObject {
    func addNewMovie(_ movie: LocalMovie)
    func updateMovie(_ movie: Movie, with editedMovie: LocalMovie)
    func doesMovieExist(_ movie: LocalMovie) -> Bool
}

protocol AddEditMoviePresenter: AnyObject {
    func setNavigationTitle(_ title: String)
    func updateHeaderView(with scrollView: UIScrollView)
    func updateHeaderView(with image: UIImage?)
    func showKeyboard(with height: CGFloat, duration: TimeInterval)
    func hideKeyboard(with duration: TimeInterval)
    func pop(completion: (() -> Void)?)
}

protocol AddEditMovieViewModelable {
    var sections: [AddEditMovieViewModel.Section] { get }
    var headerViewImage: UIImage? { get }
    var presenter: AddEditMoviePresenter? { get set }
    func screenWillAppear()
    func getNumberOfFields(in section: Int) -> Int
    func getCellViewModel(at indexPath: IndexPath) -> CellViewModelable?
    func cancelButtonTapped()
    func doneButtonTapped()
    func didScroll(with scrollView: UIScrollView)
    func keyboardWillShow(with frame: CGRect)
    func keyboardWillHide()
}

final class AddEditMovieViewModel: AddEditMovieViewModelable,
                                   Toastable {
    
    enum Mode {
        case add
        case edit(movie: Movie)
    }
    
    enum Section: Hashable {
        case posters
        case fields([Field])
    }
    
    enum Field: CaseIterable {
        case title
        case studio
        case genres
        case directors
        case writers
        case year
        case length
        case mpaRating
        case criticsRating
        case description
    }
    
    let sections: [Section]
    
    private let mode: Mode
    private let posters: [String]
    private weak var listener: AddEditMovieListener?
    
    weak var presenter: AddEditMoviePresenter?
    
    init(mode: Mode, posters: [String], listener: AddEditMovieListener?) {
        self.mode = mode
        self.posters = posters
        self.sections = [.fields(Field.allCases)]
        self.listener = listener
        setup()
    }
    
}

// MARK: - Exposed Helpers
extension AddEditMovieViewModel {
    
    var headerViewImage: UIImage? {
        switch mode {
        case .add:
            return nil
        case let .edit(movie):
            guard let poster = movie.poster else { return nil }
            return UIImage(named: poster)
        }
    }
    
    func screenWillAppear() {
        presenter?.setNavigationTitle(mode.title)
    }
    
    func cancelButtonTapped() {
        presenter?.pop(completion: nil)
    }
    
    func doneButtonTapped() {
        guard validateMovieEntry() else { return }
        
        switch self.mode {
        case .add:
            guard !(listener?.doesMovieExist(updatedMovie) ?? false) else {
                showToast(with: Constants.movieExistsErrorMessage)
                return
            }
            presenter?.pop { [weak self] in
                guard let self = self else { return }
                self.listener?.addNewMovie(self.updatedMovie)
            }
        case let .edit(movie):
            presenter?.pop { [weak self] in
                guard let self = self else { return }
                self.listener?.updateMovie(movie, with: self.updatedMovie)
            }
        }
    }
    
    func getNumberOfFields(in section: Int) -> Int {
        guard let section = sections[safe: section] else { return 0 }
        switch section {
        case .posters:
            return 1
        case let .fields(fields):
            return fields.count
        }
    }
    
    func getCellViewModel(at indexPath: IndexPath) -> CellViewModelable? {
        guard let section = sections[safe: indexPath.section] else { return nil }
        switch section {
        case .posters:
            switch mode {
            case .add:
                return PostersListCellViewModel(posters: posters, currentPoster: nil, listener: self)
            case let .edit(movie):
                return PostersListCellViewModel(posters: posters, currentPoster: movie.poster, listener: self)
            }
        case let .fields(fields):
            guard let field = fields[safe: indexPath.row] else { return nil }
            return AddEditMovieCellViewModel(mode: mode, field: field, listener: self)
        }
    }
    
    func didScroll(with scrollView: UIScrollView) {
        presenter?.updateHeaderView(with: scrollView)
    }
    
    func keyboardWillShow(with frame: CGRect) {
        presenter?.showKeyboard(with: frame.height, duration: Constants.animationDuration)
    }
    
    func keyboardWillHide() {
        presenter?.hideKeyboard(with: Constants.animationDuration)
    }
    
}

// MARK: - Private Helpers
private extension AddEditMovieViewModel {
    
    func setup() {
        // Initialize updatedMovie with the current movie
        switch mode {
        case .add:
            // Empty movie object initialized
            return
        case let .edit(movie):
            updatedMovie = LocalMovie.transform(with: movie)
        }
    }
    
    func validateMovieEntry() -> Bool {
        // Validate mandatory fields
        let mandatoryFields = Field.allCases.filter { $0.isMandatory }
        for field in mandatoryFields {
            switch field {
            case .title:
                guard updatedMovie.title == nil else { continue }
                return showError(for: field)
            case .studio:
                guard updatedMovie.studio == nil else { continue }
                return showError(for: field)
            case .genres:
                guard updatedMovie.genres == nil else { continue }
                return showError(for: field)
            case .directors:
                guard updatedMovie.directors == nil else { continue }
                return showError(for: field)
            case .writers:
                guard updatedMovie.writers == nil else { continue }
                return showError(for: field)
            case .year:
                guard updatedMovie.year == nil else { continue }
                return showError(for: field)
            case .length:
                guard updatedMovie.length == nil else { continue }
                return showError(for: field)
            case .mpaRating:
                guard updatedMovie.mpaRating == nil else { continue }
                return showError(for: field)
            case .criticsRating:
                guard updatedMovie.criticsRating == nil else { continue }
                return showError(for: field)
            case .description:
                guard updatedMovie.description == nil else { continue }
                return showError(for: field)
            }
        }
        return true
    }
    
    func showError(for field: Field) -> Bool {
        showToast(with: field.errorMessage)
        return false
    }
    
}

// MARK: - PostersListCellListener Methods
extension AddEditMovieViewModel: PostersListCellListener {
    
    func newPosterSelected(_ poster: String) {
        updatedMovie.poster = poster
        switch mode {
        case .add:
            return
        case .edit:
            let image = UIImage(named: poster)
            presenter?.updateHeaderView(with: image)
        }
    }
    
    func oldPosterDeselected() {
        updatedMovie.poster = nil
    }
    
}

// MARK: - AddEditMovieCellListener Methods
extension AddEditMovieViewModel: AddEditMovieCellListener {
    
    func movieFieldUpdated(_ field: Field, with text: String?) {
        guard let text = text else { return }
        switch field {
        case .title:
            updatedMovie.title = text
        case .studio:
            updatedMovie.studio = text
        case .genres:
            updatedMovie.genres = text
        case .directors:
            updatedMovie.directors = text
        case .writers:
            updatedMovie.writers = text
        case .year:
            guard let year = Int16(text) else { return }
            updatedMovie.year = year
        case .length:
            guard let length = Int16(text) else { return }
            updatedMovie.length = length
        case .mpaRating:
            updatedMovie.mpaRating = text
        case .criticsRating:
            guard let rating = Double(text) else { return }
            updatedMovie.criticsRating = rating
        case .description:
            updatedMovie.description = text
        }
    }
    
}

// MARK: - AddEditMovieViewModel.Mode Helpers
private extension AddEditMovieViewModel.Mode {
    
    var title: String {
        switch self {
        case .add:
            return Constants.addMovie
        case .edit:
            return Constants.editMovie
        }
    }
    
}

// MARK: - AddEditMovieViewModel.Field Helpers
private extension AddEditMovieViewModel.Field {
    
    var isMandatory: Bool {
        switch self {
        case .title, .studio, .criticsRating:
            return true
        case .genres, .directors, .writers, .year, .length, .mpaRating, .description:
            return false
        }
    }
    
    var errorMessage: String {
        return "\(placeholder) \(Constants.fieldErrorMessageSubtext)"
    }
    
}
