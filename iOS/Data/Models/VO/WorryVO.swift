//
//  WorryVO.swift
//  iOS
//
//  Created by 정지승 on 2021/07/10.
//

import Foundation

struct AddWorryVO : Codable {
    let title: String
    let body: String
}

struct AddWorryResponseVO : Codable {
    let id: Int
    let title: String
    let body: String
    let createdAt: String
}
//{
//  "body": "string",
//  "createdAt": "2021-07-10T08:37:59.816Z",
//  "id": 0,
//  "title": "string"
//}

struct WorryDataVO {
    let id: Int
    let title: String
    let body: String
    let viewCount: Int
    let tags: [String]
    let viewType: WorryDetailViewController.ViewType
}
