//
//  ViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/06/29.
//

import UIKit
import AuthenticationServices

class SignInViewController: UIViewController {

    @IBOutlet var buttonStack: UIStackView!
    @IBOutlet var appleLoginButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
//        setUpProviderLoginView()
        appleLoginButton.layer.cornerRadius = 5.0
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

