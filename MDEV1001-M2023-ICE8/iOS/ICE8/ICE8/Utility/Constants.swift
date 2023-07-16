//
//  Constants.swift
//  ICE8
//
//  Created by Abhijit Singh on 07/07/23.
//

import UIKit

struct Constants {
    
    static let goodRating: Double = 9
    static let okayRating: Double = 8
    static let mehRating: Double = 7
    static let badRating: Double = 6
    static let animationDuration: TimeInterval = 0.3
    
    static let storyboardName = "Main"
    static let favouriteMoviesTitle = "Favourite Movies"
    static let apiEndpoint = "https://mdev1001-m2023-api.onrender.com/api/list"
    static let invalidURLTitle = "Invalid URL"
    static let dataUnavailableTitle = "Data unavailable"
    static let emptyDataTitle = "No data to display"
    static let errorTitle = "Error"
    static let okTitle = "Okay"
    static let retryTitle = "Retry"
    
    static let moviesViewControllerIdentifier = String(describing: MoviesViewController.self)
    static let addEditMovieViewControllerIdentifier = String(describing: AddEditMovieViewController.self)
    static let movieCellName = String(describing: MovieTableViewCell.self)
    static let movieCellIdentifier = String(describing: MovieTableViewCell.self)
    static let addEditMovieCellName = String(describing: AddEditMovieTableViewCell.self)
    static let addEditMovieCellIdentifier = String(describing: AddEditMovieTableViewCell.self)
    
}