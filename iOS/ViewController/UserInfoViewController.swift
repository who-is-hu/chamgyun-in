//
//  UserInfoViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/06/29.
//

import UIKit
import TagListView
import KakaoSDKAuth
import KakaoSDKUser
import KakaoSDKCommon

class UserInfoViewController: UIViewController, TagListViewDelegate {

    @IBOutlet weak var containerView: UIView!
    @IBOutlet weak var pointView: UIView!
    @IBOutlet weak var tagListView: TagListView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        pointView.layer.cornerRadius = 5.0
        
        // setup tags
        setUpPrepareTags()
    }

    // MARK: - IBAction
    @IBAction func logoutButtonTouchInside(_ sender: UIButton) {
        let alertController = UIAlertController(title: "로그아웃", message: "정말 로그아웃 하시겠습니까?", preferredStyle: .alert)
        
        let actionDefault = UIAlertAction(title: "확인", style: .default, handler: { _ in
            UserApi.shared.logout() { (error) in
                if let error = error {
                    print("\(error.localizedDescription)")
                } else {
                    self.dismiss(animated: true, completion: nil)
                }
            }
        })
        
        let actionCancel = UIAlertAction(title: "취소", style: .cancel, handler: nil)
        
        alertController.addAction(actionDefault)
        alertController.addAction(actionCancel)
        
        self.present(alertController, animated: true, completion: nil)
        
        
    }
    
    // MARK: - View setup method
    /// setup prepare tags
    func setUpPrepareTags() {
        // Todo: Load from server
        let tags: [String] = ["Saaaaaaaaaaaaaaaaaaaaaaaaaple", "남성", "IT", "음식", "이성"]
        
//        tagListView.textFont.bol
        tagListView.delegate = self
        tagListView.textFont = UIFont.boldSystemFont(ofSize: 13)
        
        for tag in tags {
            tagListView.addTag("#\(tag)")
        }
        tagListView.addTag("수정하기")
    }
    
    // MARK: - TagListViewDelegate Method
    /// Push tag
    func tagPressed(_ title: String, tagView: TagView, sender: TagListView) {
        print("\(title) pushed")
    }
    
    /// Push remove button
    func tagRemoveButtonPressed(_ title: String, tagView: TagView, sender: TagListView) {
        print("\(title) remove button pushed")
        sender.removeTagView(tagView)
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
