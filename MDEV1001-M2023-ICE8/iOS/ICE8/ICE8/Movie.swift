//
//  Movie.swift
//  ICE8
//
//  Created by Abhijit Singh on 2023-07-05.
//

struct Movie: Codable
{
    let _id: String
    let movieID: String?
    let title: String
    let studio: String
    let genres: [String]
    let directors: [String]
    let writers: [String]
    let actors: [String]
    let year: Int
    let length: Int
    let shortDescription: String
    let mpaRating: String
    let criticsRating: Double
}
