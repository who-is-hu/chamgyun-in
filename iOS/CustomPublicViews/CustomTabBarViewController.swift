//
//  CustomeTabBarViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/01.
//

import UIKit

class CustomTabBarViewController: UITabBarController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // remove ellipsis baseline
        self.tabBar.items?.last?.title = "더보기"
        self.tabBar.items?.last?.image = UIImage(systemName: "ellipsis", withConfiguration: UIImage.SymbolConfiguration(weight: .black))!.imageWithoutBaseline()
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
