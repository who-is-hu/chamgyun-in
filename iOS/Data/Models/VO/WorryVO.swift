//
//  WorryVO.swift
//  iOS
//
//  Created by 정지승 on 2021/07/10.
//

import Foundation

struct WorryDataVO : Codable {
    let id: Int?
    let title: String?
    let body: String?
    let viewCount: Int?
    var tags: String?
    let viewType: WorryViewType?
    let createdAt: String?
    
    var splitTags: [String]? {
        get {
            return tags?.components(separatedBy: ",")
        }

        set {
            self.tags = newValue?.joined(separator: ",")
        }
    }
    
    private enum CodingKeys: String, CodingKey {
        case id
        case title
        case body
        case viewCount
        case tags = "tag_names"
        case viewType
        case createdAt
    }
}

enum WorryViewType : Int, Codable {
    case OX
    case N
}

struct PageableWorryDataVO: Codable {
    let content: [WorryDataVO]
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

//{
//  "content": [
//    {
//      "body": "string",
//      "createdAt": "2021-07-11T10:54:24.507Z",
//      "id": 0,
//      "title": "string"
//    }
//  ],
//  "empty": true,
//  "first": true,
//  "last": true,
//  "number": 0,
//  "numberOfElements": 0,
//  "pageable":
//  "size": 0
//  "sort":
//  "totalElements": 0,
//  "totalPages": 0
//}
