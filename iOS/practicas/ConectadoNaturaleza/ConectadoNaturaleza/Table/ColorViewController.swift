//
//  ColorsViewController.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Reyes Espino on 22/04/20.
//  Copyright © 2020 Marisol Reyes Espino. All rights reserved.
//

import UIKit
import Alamofire
import PromiseKit

class ColorViewController: UIViewController {
    
    @IBOutlet weak var colorTableView: UITableView!
    var dataSource = [Color]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        colorTableView.dataSource = self
        colorTableView.register(UINib(nibName: "ColorsTableViewCell", bundle: nil), forCellReuseIdentifier: "ColorsTableViewCell")
        colorTableView.delegate = self
        colorTableView.reloadData()
        
        let getColorApi = ColorApi()
            getColorApi.getColors()
            .done { json -> Void in
                /*
                for idx in json.colors! {
                    print(idx.name!)
                }
                */
                
                self.dataSource = json.colors!
                self.colorTableView.reloadData()
            }
            .catch { error in
                print(error.localizedDescription)
            }

    }
}

// MARK: UITableViewDelegate
    extension ColorViewController: UITableViewDelegate {
        func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
            print("La celda \(indexPath.row)")
        }
    }

// MARK: UITableViewDataSource
   extension ColorViewController: UITableViewDataSource {
        func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
            return dataSource.count
            
        }
        func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
            let cell = colorTableView.dequeueReusableCell(withIdentifier: "ColorsTableViewCell", for: indexPath)
            
            if let newCell = cell as? ColorsTableViewCell {
                newCell.setupCell(color: dataSource[indexPath.row].color!, service: dataSource[indexPath.row].name!)
            }
            
            return cell
        }
   }

