//
//  PopularityViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/03.
//

import UIKit


class PopularityViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    // MARk: IBOutlet
    @IBOutlet weak var tableView: UITableView!
    
    // MARK: - Lifecycle Method
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
    }
    

    // MARK: - Delegate
    // MARK: TableView
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        return UITableViewCell()
    }

}
