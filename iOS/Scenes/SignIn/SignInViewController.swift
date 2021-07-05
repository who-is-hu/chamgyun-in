//
//  ViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/06/29.
//

import UIKit
//import AuthenticationServices
import KakaoSDKCommon
import KakaoSDKAuth
import KakaoSDKUser

class SignInViewController: UIViewController {

    // MARK: - IBOutlet
    @IBOutlet var buttonStack: UIStackView!
    @IBOutlet var appleLoginButton: UIButton!
    
    // MARK: - LifeCycle Method
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        // Kakao Login Check
        if AuthApi.hasToken() {
            UserApi.shared.accessTokenInfo { (_, error) in
                if let _ = error {
                    // do nothing
                } else {
                    if let accessToken = TokenManager.manager.getToken()?.accessToken {
    //                        print("access token \(accessToken)")
                        
                        self.connectWithServer(accessToken: accessToken)
                    }
                    
                }
            }
        }
        
//        setUpProviderLoginView()
        appleLoginButton.layer.cornerRadius = 5.0
    }

    // MARK: - IBAction
    @IBAction func kakaoLoginTouchInside(_ sender: UIButton) {
        if UserApi.isKakaoTalkLoginAvailable() {
            UserApi.shared.loginWithKakaoTalk() {(oauthToken, error) in
                if let error = error {
                    print(error)
                } else if let oauthToken = oauthToken {
                    print("kakao login success with token(Access : \(oauthToken.accessToken), Refresh : \(oauthToken.refreshToken)")
                    
                    // Save token to server
                    self.connectWithServer(accessToken: oauthToken.accessToken)
                }
            }
        }
    }
    
    
    // MARK: - functions
    
    /// Connect Server with access token. if it works success then segue main view
    /// - Parameter accessToken: access token like kakao access token
    func connectWithServer(accessToken: String) {
        let param: [String: Any] = ["provider": SocialProvider.kakao.rawValue, "social_access_token": accessToken]
        
//                        print(param)
        
        APIRequest().request(url: APIRequest.authSignInUrl, method: "POST", voType: SignInVo.self, param: param) { success, data in
            if success {
//                                print("\(data)")
                
                DispatchQueue.main.async {
                    let customTabBarViewController = self.storyboard?.instantiateViewController(identifier: "CustomTabBarViewController")
                    customTabBarViewController?.modalTransitionStyle = .coverVertical
                    self.present(customTabBarViewController!, animated: false, completion: nil)
                    
                    AuthManager.shared.userInfo = data as? SignInVo
                }
            }
        }
    }
}

