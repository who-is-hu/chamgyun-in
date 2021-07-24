//
//  AuthManager.swift
//  iOS
//
//  Created by 정지승 on 2021/07/04.
//

import Foundation

class AuthManager {
    static let shared: AuthManager = AuthManager()
    private let userTokenKey: String = "Chamgyun-in-TokenKey"
    private var accessDataFromServer: SignInVo?
    
    var userToken: String? {
        get {
            return UserDefaults.standard.string(forKey: userTokenKey)
        }
        
        set {
            UserDefaults.standard.setValue(newValue, forKey: userTokenKey)
        }
    }
    
    // get UserInfo(SignInVO) struct when signin application
    var userInfo: SignInVo? {
        get {
            return accessDataFromServer
        }
        
        set {
            self.accessDataFromServer = newValue
        }
    }
}
