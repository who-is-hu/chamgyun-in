//
//  SimpleWorryTableViewCell.swift
//  iOS
//
//  Created by 정지승 on 2021/07/10.
//

import UIKit

class SimpleWorryTableViewCell: UITableViewCell {
    @IBOutlet weak var simpleTitle: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
