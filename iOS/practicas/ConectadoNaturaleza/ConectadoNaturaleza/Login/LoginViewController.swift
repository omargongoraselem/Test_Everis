//
//  AccessViewController.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Perez Rangel on 24/04/20.
//  Copyright © 2020 Marisol Perez Rangel. All rights reserved.
//

import UIKit
import PromiseKit
import Alamofire

class LoginViewController: UIViewController {
    
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var emailSwitch: UISwitch!

    @IBAction func loginButtonAction(_ sender: Any) {
        
        let getLoginApi = LoginApi()
        getLoginApi.getUserInfo()
        .done { json -> Void in
            //if json.id! == "eve.holt@reqres.in", json.token! == "password" {
                print(json.id!)
                print(json.token!)
                self.performSegue(withIdentifier: "login_segue", sender: nil)
            //} else {
                print("Credenciales invalidas")
            //}
        }
        .catch { error in
            print(error.localizedDescription)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    func dataLogin(email: String, password: String) {
        let email = emailTextField.text
        let password = emailTextField.text
    }
    
}
