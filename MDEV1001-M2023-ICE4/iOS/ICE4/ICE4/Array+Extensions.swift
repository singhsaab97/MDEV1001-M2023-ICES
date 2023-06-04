//
//  Array+Extensions.swift
//  ICE4
//
//  Created by Abhijit Singh on 03/06/23.
//

import Foundation

extension Array where Element: Hashable {
    
    subscript(safe index: Index) -> Element? {
        return indices.contains(index) ? self[index] : nil
    }
    
}
