//
//  Common.swift
//  iOS
//
//  Created by 정지승 on 2021/07/12.
//

import Foundation

struct Pageable: Codable {
    let offset: Int
    let pageNumber: Int
    let pageSize: Int
    let paged: Bool
    let sort: Sort
    let unpaged: Bool
}

struct Sort: Codable {
    let empty: Bool
    let sorted: Bool
    let unsorted: Bool
}

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
