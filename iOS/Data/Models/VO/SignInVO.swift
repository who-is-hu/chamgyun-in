//
//  SignInVO.swift
//  iOS
//
//  Created by 정지승 on 2021/07/05.
//

import Foundation

enum SocialProvider: String {
    case kakao = "KAKAO"
    case local = "LOCAL"
}

struct SignInVo: Codable {
    let email: String
    let id: Int
}
