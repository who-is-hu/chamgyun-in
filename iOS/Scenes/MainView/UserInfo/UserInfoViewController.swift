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

class UserInfoViewController: UIViewController {
    // MARK: - Properties
    var myWorryDataSource: [WorryDataVO] = []
    var ansWorryDataSource: [WorryDataVO] = []
    
    // MARK: - IBOutlet
    @IBOutlet weak var pointView: UIView!
    @IBOutlet weak var worryPointLabel: UILabel!
    @IBOutlet weak var tagListView: TagListView!
    @IBOutlet weak var userEmailView: UILabel!
    @IBOutlet weak var userNameView: UILabel!
    @IBOutlet weak var userProfileImageView: UIImageView!
    @IBOutlet weak var myWorryTableView: UITableView!
    @IBOutlet weak var ansWorryTableView: UITableView!
    
    // MARK: - override Method
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        pointView.layer.cornerRadius = 5.0
        userProfileImageView.layer.cornerRadius = userProfileImageView.frame.width / 2
        
        let nibId = String(describing: SimpleWorryTableViewCell.self)
        let nib = UINib(nibName: nibId, bundle: nil)
        
        myWorryTableView.register(nib, forCellReuseIdentifier: nibId)
        ansWorryTableView.register(nib, forCellReuseIdentifier: nibId)
        
        myWorryTableView.rowHeight = UITableView.automaticDimension
        ansWorryTableView.rowHeight = UITableView.automaticDimension
        
        myWorryTableView.estimatedRowHeight = 44
        ansWorryTableView.estimatedRowHeight = 44
        
        myWorryTableView.delegate = self
        myWorryTableView.dataSource = self
        ansWorryTableView.delegate = self
        ansWorryTableView.dataSource = self
        
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        // setup user email, name and profile image
        setUpUserInfo()
        
        // setup tags
        setUpPrepareTags()
        
        // load data
        loadMyWorryData()
        loadAnsWorryData()
        loadTagData()
        loadWorryPointData()
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
    
    @IBAction func myWorryDetailButtonTouchInside(_ sender: UIButton) {
        if let worryViewController = storyboard?.instantiateViewController(identifier: "WorryViewStoryboard") as? WorryViewController {
            worryViewController.title = "내고민"
            worryViewController.loadType = .MyWorry
            self.navigationController?.pushViewController(worryViewController, animated: true)
        }
    }
    
    @IBAction func ansWorryDetailButtonTouchInside(_ sender: UIButton) {
        if let worryViewController = storyboard?.instantiateViewController(identifier: "WorryViewStoryboard") as? WorryViewController {
            worryViewController.title = "답한 고민"
            worryViewController.loadType = .AnsWorry
            self.navigationController?.pushViewController(worryViewController, animated: true)
        }
    }
    
    // MARK: - View setup method
    /// setup prepare tags
    func setUpPrepareTags() {
        // Todo: Load from server
        tagListView.delegate = self
        tagListView.textFont = UIFont.boldSystemFont(ofSize: 13)
    }
    
    func setUpUserInfo() {
        UserApi.shared.me() { user, error in
            if let error = error {
                print(error)
            } else {
                self.userEmailView.text = user?.kakaoAccount?.email ?? "사용자 동의 필요"
                self.userNameView.text = user?.kakaoAccount?.profile?.nickname ?? "비공개"
                
                if let url: URL = user?.kakaoAccount?.profile?.profileImageUrl {
                    DispatchQueue.global().async() {
                        let data = try? Data(contentsOf: url)
                        DispatchQueue.main.async {
                            self.userProfileImageView.image = UIImage(data: data!)
                        }
                    }
                }
            }
        }
    }
    
    func loadMyWorryData() {
        let display: Int = 10

        APIRequest().request(url: "\(APIRequest.worryPostUrl)/my?page=0&size=\(display)", method: "GET", voType: PageableWorryDataVO.self) { success, data in
            if success {
                if let data = data as? PageableWorryDataVO {
                    self.myWorryDataSource = data.content
                    
                    DispatchQueue.main.async {
                        self.myWorryTableView.reloadData()
                    }
                    
                    print(data)
                }
            }
        }

    }
    
    func loadAnsWorryData() {
        let display: Int = 10

        APIRequest().request(url: "\(APIRequest.votePostUrl)/participate-post?page=0&size=\(display)", method: "GET", voType: PageableWorryDataVO.self) { success, data in
            if success {
                if let data = data as? PageableWorryDataVO {
                    self.ansWorryDataSource = data.content
                    
                    DispatchQueue.main.async {
                        self.ansWorryTableView.reloadData()
                    }
                    
                    print(data)
                }
            }
        }

    }
    
    func loadTagData() {
        APIRequest().request(url: APIRequest.tagGetPatchUrl, method: "GET", voType: [TagVO].self) { success, data in
            if success {
                print(data)
                
                DispatchQueue.main.async {
                    self.tagListView.removeAllTags()
                    
                    if let tags = data as? [TagVO] {
                        for tag in tags {
                            self.tagListView.addTag("#\(tag.name)")
                        }
                    }
                }
                
            }
        }
    }
    
    func loadWorryPointData() {
        APIRequest().request(url: APIRequest.userPointGetUrl, method: "GET", voType: UserPointVO.self) { success, data in
            guard success, let data = data as? UserPointVO else {
                return
            }
            
            DispatchQueue.main.async {
                self.worryPointLabel.text = "\(data.point) P"
            }
        }
    }

}

// MARK: - Extension and delegate
// tableView

extension UserInfoViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let selectedVO: WorryDataVO
        
        if tableView == myWorryTableView {
            print("myWorry selected row : \(indexPath.row)")
            selectedVO = myWorryDataSource[indexPath.row]
        } else {
            print("ansWorry selected row : \(indexPath.row)")
            selectedVO = ansWorryDataSource[indexPath.row]
        }
        
        tableView.deselectRow(at: indexPath, animated: true)
        
        if let worryDetailViewController = storyboard?.instantiateViewController(identifier: "WorryDetailStoryboard") as? WorryDetailViewController {
            worryDetailViewController.postId = selectedVO.id
            self.navigationController?.pushViewController(worryDetailViewController, animated: true)
        }
    }
}

extension UserInfoViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if tableView == myWorryTableView {
            return myWorryDataSource.count
        } else {
            return ansWorryDataSource.count
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: SimpleWorryTableViewCell.self)) as! SimpleWorryTableViewCell
        
        if tableView == myWorryTableView {
            cell.simpleTitle.text = myWorryDataSource[indexPath.row].title
        } else {
            cell.simpleTitle.text = ansWorryDataSource[indexPath.row].title
        }
        
        return cell
    }
    
    
}

// taglistview
extension UserInfoViewController: TagListViewDelegate {
    /// Push tag
    func tagPressed(_ title: String, tagView: TagView, sender: TagListView) {
        print("\(title) pushed")
        
    }
    
    /// Push remove button
    func tagRemoveButtonPressed(_ title: String, tagView: TagView, sender: TagListView) {
        print("\(title) remove button pushed")
        sender.removeTagView(tagView)
    }
}
