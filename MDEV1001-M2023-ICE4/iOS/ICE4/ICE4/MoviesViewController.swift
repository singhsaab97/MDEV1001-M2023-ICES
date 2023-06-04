//
//  MoviesViewController.swift
//  ICE4
//
//  Created by Abhijit Singh on 03/06/23.
//

import UIKit

final class MoviesViewController: UIViewController {
    
    private lazy var tableView: UITableView = {
        let view = UITableView(frame: CGRect(), style: .plain)
        view.translatesAutoresizingMaskIntoConstraints = false
        view.backgroundColor = .clear
        view.rowHeight = UITableView.automaticDimension
        view.delegate = self
        view.dataSource = self
        view.register(MovieTableViewCell.self, forCellReuseIdentifier: MovieTableViewCell.reuseId)
        return view
    }()
    
    private var viewModel: MoviesViewModelable
    
    init(viewModel: MoviesViewModelable) {
        self.viewModel = viewModel
        super.init(nibName: nil, bundle: nil)
        self.viewModel.presenter = self
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setup()
    }
    
}

// MARK: - Private Helpers
private extension MoviesViewController {
    
    func setup() {
        view.backgroundColor = .systemBackground
        addTableView()
        viewModel.screenLoaded()
    }
    
    func addTableView() {
        view.addSubview(tableView)
        tableView.topAnchor.constraint(equalTo: view.topAnchor).isActive = true
        tableView.bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true
        tableView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        tableView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
    }
    
}

// MARK: - UITableViewDelegate Methods
extension MoviesViewController: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
}

// MARK: - UITableViewDataSource Methods
extension MoviesViewController: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.movies.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let movieCell = tableView.dequeueReusableCell(
                withIdentifier: MovieTableViewCell.reuseId,
                for: indexPath
            ) as? MovieTableViewCell,
              let movie = viewModel.movies[safe: indexPath.row] else { return UITableViewCell() }
        movieCell.configure(with: movie)
        return movieCell
    }
    
}

// MARK: - MoviesPresenter Methods
extension MoviesViewController: MoviesPresenter {
    
    func reloadData() {
        tableView.reloadData()
    }
    
}
