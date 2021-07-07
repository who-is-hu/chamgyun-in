//
//  ChooseWorryNTableViewCell.swift
//  iOS
//
//  Created by 정지승 on 2021/07/07.
//

import UIKit
import M13Checkbox

class ChooseWorryNTableViewCell: UITableViewCell {

    @IBOutlet weak var mCheckBox: M13Checkbox!
    @IBOutlet weak var queryLable: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
