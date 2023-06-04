//
//  MoviesViewModel.swift
//  ICE4
//
//  Created by Abhijit Singh on 03/06/23.
//

import Foundation
import CoreData

protocol MoviesPresenter: AnyObject {
    func reloadData()
}

protocol MoviesViewModelable {
    var movies: [Movie] { get }
    var presenter: MoviesPresenter? { get set }
    func screenLoaded()
}

final class MoviesViewModel: MoviesViewModelable {
    
    private(set) var movies = [Movie]()
        
    private lazy var persistentContainer: NSPersistentContainer = {
        let container = NSPersistentContainer(name: "ICE4")
        container.loadPersistentStores { (storeDescription, error) in
            if let nsError = error as NSError? {
                fatalError("Unresolved error \(nsError), \(nsError.userInfo)")
            }
        }
        return container
    }()
    
    weak var presenter: MoviesPresenter?
    
    init() {
        self.movies = []
        saveData()
    }
    
}

// MARK: - Exposed Helpers
extension MoviesViewModel {
    
    func screenLoaded() {
        loadData()
    }
    
}

// MARK: - Private Helpers
private extension MoviesViewModel {
    
    /// Stores and persists data from `movies.json` if context doesn't exist
    func saveData() {
        guard let url = Bundle.main.url(forResource: "Movies", withExtension: "json"),
              let data = try? Data(contentsOf: url),
              let jsonArray = try? JSONSerialization.jsonObject(with: data, options: []) as? [[String: Any]] else { return }
        let context = persistentContainer.viewContext
        for object in jsonArray {
            let movieId = object["movieID"] as? Int16 ?? .zero
            guard !doesMovieExist(with: movieId) else { continue }
            let movie = Movie(context: context)
            movie.movie_id = movieId
            movie.title = object["title"] as? String
            movie.studio = object["studio"] as? String
            movie.genres = object["genres"] as? String
            movie.directors = object["directors"] as? String
            movie.writers = object["writers"] as? String
            movie.actors = object["actors"] as? String
            movie.year = object["year"] as? Int16 ?? .zero
            movie.length = object["length"] as? Int16 ?? .zero
            movie.short_description = object["shortDescription"] as? String
            movie.mpa_rating = object["mpaRating"] as? String
            movie.critics_rating = object["criticsRating"] as? Double ?? .zero
        }
        if context.hasChanges {
            do {
                try context.save()
            } catch {
                let nsError = error as NSError
                fatalError("Unresolved error \(nsError), \(nsError.userInfo)")
            }
        }
    }
    
    /// Load stored data from persistent container
    func loadData() {
        let context = persistentContainer.viewContext
        let request = Movie.fetchRequest()
        do {
            movies = try context.fetch(request)
            movies = movies.sorted(by: { $0.movie_id < $1.movie_id })
            presenter?.reloadData()
        } catch {
            let nserror = error as NSError
            fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
        }
    }
    
    /// Check if a movie exists in the existing data model
    func doesMovieExist(with id: Int16) -> Bool {
        let context = persistentContainer.viewContext
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Movie")
        request.predicate = NSPredicate(format: "movie_id == %@", String(id))
        do {
            let results = try context.fetch(request)
            return results.count > 0
        } catch {
            let nserror = error as NSError
            fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
        }
    }
    
}
