//
//  TagVO.swift
//  iOS
//
//  Created by 정지승 on 2021/07/12.
//

import Foundation

struct TagVO : Codable {
    let id: Int
    let name: String
    let postNumber: Int?
    
    init(id: Int, name: String, postNumber: Int = 0) {
        self.id = id
        self.name = name
        self.postNumber = postNumber
    }
    
    enum CodingKeys: String, CodingKey {
        case id
        case name
        case postNumber = "num_post"
    }
}

struct PageableTagVO: Codable {
    let content: [TagVO]
    let empty: Bool
    let first: Bool
    let last: Bool
    let number: Int
    let numberOfElements: Int
    let pageable: Pageable
    let sort: Sort
    let size: Int
    let totalElements: Int
    let totalPages: Int
}
