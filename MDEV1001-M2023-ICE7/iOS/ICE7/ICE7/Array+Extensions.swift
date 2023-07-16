//
//  Array+Extensions.swift
//  ICE8
//
//  Created by Abhijit Singh on 07/07/23.
//

import Foundation

extension Array {
    
    subscript(safe index: Index) -> Element? {
        return indices.contains(index) ? self[index] : nil
    }
    
}
