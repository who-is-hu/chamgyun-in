//
//  WorryVO.swift
//  iOS
//
//  Created by 정지승 on 2021/07/10.
//

import Foundation

struct WorryDataRVO : Codable {
    let id: Int
    let title: String
    let body: String
    let createdAt: String
}

struct WorryDataVO : Codable {
    let id: Int?
    let title: String
    let body: String
    let viewCount: Int?
    let tags: [String]?
    let viewType: WorryViewType?
    let createdAt: String?
    
//    init(id: Int = -1, title: String, body: String, viewCount: Int, tags: [String], viewType: WorryViewType = .OX, createdAt: String = "") {
//        self.id = id
//        self.title = title
//        self.body = body
//    }
}

enum WorryViewType : Int, Codable {
    case OX
    case N
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
//  "pageable": {
//    "offset": 0,
//    "pageNumber": 0,
//    "pageSize": 0,
//    "paged": true,
//    "sort": {
//      "empty": true,
//      "sorted": true,
//      "unsorted": true
//    },
//    "unpaged": true
//  },
//  "size": 0,
//  "sort": {
//    "empty": true,
//    "sorted": true,
//    "unsorted": true
//  },
//  "totalElements": 0,
//  "totalPages": 0
//}
