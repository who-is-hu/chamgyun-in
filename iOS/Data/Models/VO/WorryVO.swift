//
//  WorryVO.swift
//  iOS
//
//  Created by 정지승 on 2021/07/10.
//

import Foundation

struct WorryDataDetailVO : Codable {
    let id: Int?
    let owner: Int?
    let title: String?
    let body: String?
    let tags: String?
    let choices: [WorryChooseItem]?
    let state: String?
    let voteType: String?
    let worryType: String?
    let voted: Bool?
    let createdAt: String?
    
    var splitTags: [String]? {
        get {
            return tags?.components(separatedBy: ",")
        }
    }
    
    var worryEnumType: WorryViewType? {
        get {
            guard let worryType = self.worryType else {
                return nil
            }
            
            
            if worryType == "MULTIPLE_CHOICES_WORRY" {
                return .N
            } else {
                return .OX
            }
        }
    }
    
    private enum CodingKeys: String, CodingKey {
        case id
        case owner
        case title
        case body
        case tags = "tag_names"
        case choices
        case state
        case voteType = "vote_type"
        case worryType = "worry_type"
        case voted = "is_voted"
        case createdAt
    }
}

enum WorryViewType: String {
    case OX = "OX_CHOICES_WORRY"
    case N = "MULTIPLE_CHOICES_WORRY"
}

enum VoteType: String {
    case ONE = "SELECT_ONE"
    case MILTIPLE = "SELECT_MULTIPLE"
}

struct WorryChooseItem: Codable {
    let id: Int?
    let name: String?
    let votedNumber: Int?
    
    private enum CodingKeys: String, CodingKey {
        case id
        case name
        case votedNumber = "num_user"
    }
}

struct WorryDataVO : Codable {
    let id: Int?
    let owner: Int?
    let title: String?
    let body: String?
    let tags: String?
    let voted: Bool?
    
    var splitTags: [String]? {
        get {
            return tags?.components(separatedBy: ",")
        }
    }
    
    private enum CodingKeys: String, CodingKey {
        case id
        case owner
        case title
        case body
        case tags = "tag_names"
        case voted = "is_voted"
    }
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
