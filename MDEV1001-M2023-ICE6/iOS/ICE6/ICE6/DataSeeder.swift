//
//  DataSeeder.swift
//  ICE6
//
//  Created by Abhijit Singh on 2023-05-31.
//

import Foundation
import UIKit
import CoreData

func seedData() {
    guard let url = Bundle.main.url(forResource: "movies", withExtension: "json") else {
        print("JSON file not found.")
        return
    }
    
    guard let data = try? Data(contentsOf: url) else {
        print("Failed to read JSON file.")
        return
    }
    
    guard let jsonArray = try? JSONSerialization.jsonObject(with: data, options: []) as? [[String: Any]] else {
        print("Failed to parse JSON.")
        return
    }
    
    guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
        print("AppDelegate not found.")
        return
    }
    
    let context = appDelegate.persistentContainer.viewContext
    
    for jsonObject in jsonArray {
        let movie = Movie(context: context)
        
        movie.movieid = jsonObject["movieID"] as? Int16 ?? 0
        movie.title = jsonObject["title"] as? String
        movie.studio = jsonObject["studio"] as? String
        movie.genres = jsonObject["genres"] as? String
        movie.directors = jsonObject["directors"] as? String
        movie.writers = jsonObject["writers"] as? String
        movie.actors = jsonObject["actors"] as? String
        movie.year = jsonObject["year"] as? Int16 ?? 0
        movie.length = jsonObject["length"] as? Int16 ?? 0
        movie.shortdescription = jsonObject["shortDescription"] as? String
        movie.mparating = jsonObject["mpaRating"] as? String
        movie.criticsrating = jsonObject["criticsRating"] as? Double ?? 0.0
        
        // Save the context after each movie is created
        do {
            try context.save()
        } catch {
            print("Failed to save movie: \(error)")
        }
    }
    
    print("Data seeded successfully.")
}

func deleteAllData() {
    let persistentContainer = NSPersistentContainer(name: "ICE6")
    persistentContainer.loadPersistentStores { _, error in
        guard error == nil else {
            print("Failed to load persistent stores: \(error!)")
            return
        }
        let context = persistentContainer.viewContext
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "Movie")
        let deleteRequest = NSBatchDeleteRequest(fetchRequest: fetchRequest)
        
        do {
            try context.execute(deleteRequest)
            try context.save()
            print("All data deleted successfully.")
        } catch {
            print("Failed to delete all data: \(error)")
        }
    }
}
