//
//  MovieCellViewModel.swift
//  ICE7
//
//  Created by Abhijit Singh on 07/07/23.
//

import Foundation

protocol MovieCellViewModelable {
    var movie: Movie { get }
    var isExpanded: Bool { get }
    var state: MovieCellViewModel.RatingState { get }
}

final class MovieCellViewModel: MovieCellViewModelable {
    
    /// Determines the background color of movie rating
    enum RatingState {
        case good
        case okay
        case meh
        case bad
        case unknown
    }
    
    let movie: Movie
    let isExpanded: Bool
    
    private(set) var state: RatingState
    
    init(movie: Movie, isExpanded: Bool) {
        self.movie = movie
        self.isExpanded = isExpanded
        self.state = .unknown
        setRatingState()
    }
    
}

// MARK: - Private Helpers
private extension MovieCellViewModel {
    
    func setRatingState() {
        if movie.criticsRating >= Constants.goodRating {
            state = .good
        } else if movie.criticsRating >= Constants.okayRating {
            state = .okay
        } else if movie.criticsRating >= Constants.mehRating {
            state = .meh
        } else {
            state = .bad
        }
    }
    
}
