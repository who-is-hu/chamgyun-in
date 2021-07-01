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

    @IBOutlet var buttonStack: UIStackView!
    @IBOutlet var appleLoginButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        // Kakao Login Check
        if AuthApi.hasToken() {
            UserApi.shared.accessTokenInfo { (_, error) in
                if let _ = error {
                    // do nothing
                } else {
                    let customTabBarViewController = self.storyboard?.instantiateViewController(identifier: "CustomTabBarViewController")
                    customTabBarViewController?.modalTransitionStyle = .coverVertical
                    self.present(customTabBarViewController!, animated: false, completion: nil)
                }
            }
        }
        
//        setUpProviderLoginView()
        appleLoginButton.layer.cornerRadius = 5.0
    }

    
    @IBAction func kakaoLoginTouchInside(_ sender: UIButton) {
        if UserApi.isKakaoTalkLoginAvailable() {
            UserApi.shared.loginWithKakaoTalk() {(oauthToken, error) in
                if let error = error {
                    print(error)
                } else if let oauthToken = oauthToken {
                    print("kakao login success with token(Access : \(oauthToken.accessToken), Refresh : \(oauthToken.refreshToken)")
                    
                    // Save token to server
                    
                    
                    // Present MainView
                    let customTabBarViewController = self.storyboard?.instantiateViewController(identifier: "CustomTabBarViewController")
                    customTabBarViewController?.modalTransitionStyle = .coverVertical
                    self.present(customTabBarViewController!, animated: true, completion: nil)
                }
            }
        }
    }

//    func setUpProviderLoginView() {
//        let button = ASAuthorizationAppleIDButton()
//        button.addTarget(self, action: #selector(donothing), for: .touchUpInside)
//        buttonStack.addSubview(button)
//    }
    
    @objc func donothing() {
        print("donothing")
    }
    
}

