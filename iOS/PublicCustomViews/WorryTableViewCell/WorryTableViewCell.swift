//
//  WorryTableViewCell.swift
//  iOS
//
//  Created by 정지승 on 2021/07/03.
//

import UIKit
import TagListView

class WorryTableViewCell: UITableViewCell {
    
    // MARK: - IBOutlet
    @IBOutlet weak var titleView: UILabel!
    @IBOutlet weak var bodyView: UILabel!
    @IBOutlet weak var tagListView: TagListView!
    @IBOutlet weak var ansStateButton: UIButton!
    
    // MARK: - Lifecycle Method
    override class func awakeFromNib() {
        super.awakeFromNib()
    }

}
