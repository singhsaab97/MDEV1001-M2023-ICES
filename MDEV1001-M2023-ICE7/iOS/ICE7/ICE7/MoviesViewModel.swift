//
//  MoviesViewModel.swift
//  ICE7
//
//  Created by Abhijit Singh on 07/07/23.
//

import UIKit

protocol MoviesViewModelPresenter: AnyObject {
    func startLoading()
    func stopLoading()
    func reload()
    func reloadRows(at indexPaths: [IndexPath])
    func present(_ viewController: UIViewController)
}

protocol MoviesViewModelable {
    var title: String { get }
    var movies: [Movie] { get }
    var presenter: MoviesViewModelPresenter? { get set }
    func screendDidLoad()
    func getCellViewModel(at indexPath: IndexPath) -> MovieCellViewModelable?
    func didSelectMovie(at indexPath: IndexPath)
}

final class MoviesViewModel: MoviesViewModelable {
    
    private(set) var movies: [Movie]
    /// Keeps a track of all the movie ids corresponding to a flag based on expanded or collapsed state
    private var isExpandedDict: [String: Bool]
    
    weak var presenter: MoviesViewModelPresenter?
    
    init() {
        self.movies = []
        self.isExpandedDict = [:]
    }
    
}

// MARK: - Exposed Helpers
extension MoviesViewModel {
    
    var title: String {
        return Constants.favouriteMoviesTitle
    }
    
    func screendDidLoad() {
        fetchMovies()
    }
    
    func getCellViewModel(at indexPath: IndexPath) -> MovieCellViewModelable? {
        guard let movie = movies[safe: indexPath.row],
              let isExpanded = isExpandedDict[movie.id] else { return nil }
        return MovieCellViewModel(movie: movie, isExpanded: isExpanded)
    }
    
    func didSelectMovie(at indexPath: IndexPath) {
        guard let movie = movies[safe: indexPath.row],
              let isExpanded = isExpandedDict[movie.id] else { return }
        isExpandedDict[movie.id] = !isExpanded
        presenter?.reloadRows(at: [indexPath])
    }
    
}

// MARK: - Private Helpers
private extension MoviesViewModel {
    
    func fetchMovies() {
        presenter?.startLoading()
        guard let url = URL(string: Constants.apiEndpoint) else {
            prepareAlert(with: Constants.invalidURLTitle)
            return
        }
        let request = URLRequest(url: url)
        URLSession.shared.dataTask(with: request) { [weak self] (data, _, error) in
            self?.performOnMainThread { [weak self] in
                guard let error = error else {
                    guard let data = data else {
                        self?.prepareAlert(with: Constants.dataUnavailableTitle)
                        return
                    }
                    guard !data.isEmpty else {
                        self?.prepareAlert(with: Constants.emptyDataTitle)
                        return
                    }
                    self?.parseMovies(from: data)
                    return
                }
                self?.prepareAlert(with: Constants.errorTitle, message: error.localizedDescription, canRetry: true)
            }
        }.resume()
    }
    
    func parseMovies(from data: Data) {
        presenter?.stopLoading()
        do {
            movies = try JSONDecoder().decode([Movie].self, from: data)
            movies.forEach { movie in
                // Set initially collapsed
                isExpandedDict[movie.id] = false
            }
            presenter?.reload()
        } catch {
            prepareAlert(with: Constants.errorTitle, message: error.localizedDescription)
        }
    }
    
    func prepareAlert(with title: String, message: String? = nil, canRetry: Bool = false) {
        // Alert is show only in case of failure
        presenter?.stopLoading()
        let alertController = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let okAction = UIAlertAction(title: Constants.okTitle, style: .cancel)
        alertController.addAction(okAction)
        if canRetry {
            let retryAction = UIAlertAction(title: Constants.retryTitle, style: .default) { [weak self] _ in
                self?.fetchMovies()
            }
            alertController.addAction(retryAction)
        }
        presenter?.present(alertController)
    }
    
    func performOnMainThread(task: @escaping () -> Void) {
        DispatchQueue.main.async {
            task()
        }
    }
    
}
