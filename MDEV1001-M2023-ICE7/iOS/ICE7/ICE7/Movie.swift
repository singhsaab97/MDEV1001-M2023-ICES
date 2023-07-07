//
//  Movie.swift
//  ICE7
//
//  Created by Abhijit Singh on 07/07/23.
//

import Foundation

struct Movie: Codable {
    let id: String
    let title: String
    let studio: String
    let genres: [String]
    let directors: [String]
    let writers: [String]
    let actors: [String]
    let year: Int
    let length: Int
    let mpaRating: String
    let criticsRating: Double
    let description: String?
    
    private enum CodingKeys: String, CodingKey {
        case id = "_id"
        case title
        case studio
        case genres
        case directors
        case writers
        case actors
        case year
        case length
        case description = "shortDescription"
        case mpaRating
        case criticsRating
    }
    
}
