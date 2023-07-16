//
//  MoviesViewController.swift
//  ICE8
//
//  Created by Abhijit Singh on 07/07/23.
//

import UIKit

final class MoviesViewController: UIViewController {
    
    static let identifier = Constants.moviesViewControllerIdentifier
    
    @IBOutlet private weak var tableView: UITableView!
    @IBOutlet private weak var spinnerView: UIActivityIndicatorView!
    
    var viewModel: MoviesViewModelable?

    override func viewDidLoad() {
        super.viewDidLoad()
        setup()
    }

}

// MARK: - Private Helpers
private extension MoviesViewController {
    
    func setup() {
        navigationItem.title = viewModel?.title
        registerMovieCell()
        viewModel?.presenter = self
        viewModel?.screendDidLoad()
    }
    
    func registerMovieCell() {
        let nib = UINib(nibName: MovieTableViewCell.identifier, bundle: nil)
        tableView.register(nib, forCellReuseIdentifier: MovieTableViewCell.identifier)
    }
    
}

// MARK: - UITableViewDelegate Methods
extension MoviesViewController: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        viewModel?.didSelectMovie(at: indexPath)
    }
    
}

// MARK: - UITableViewDataSource Methods
extension MoviesViewController: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel?.movies.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let movieCell = tableView.dequeueReusableCell(
                withIdentifier: MovieTableViewCell.identifier,
                for: indexPath
            ) as? MovieTableViewCell,
            let viewModel = viewModel?.getCellViewModel(at: indexPath) else { return UITableViewCell() }
        movieCell.configure(with: viewModel)
        return movieCell
    }
    
}

// MARK: - MoviesViewModelPresenter Methods
extension MoviesViewController: MoviesViewModelPresenter {
    
    func startLoading() {
        spinnerView.isHidden = false
        spinnerView.startAnimating()
    }
    
    func stopLoading() {
        spinnerView.isHidden = true
        spinnerView.stopAnimating()
    }
    
    func reload() {
        tableView.reloadData()
    }
    
    func reloadRows(at indexPaths: [IndexPath]) {
        tableView.reloadRows(at: indexPaths, with: .fade)
    }
    
    func present(_ viewController: UIViewController) {
        present(viewController, animated: true)
    }
    
}
