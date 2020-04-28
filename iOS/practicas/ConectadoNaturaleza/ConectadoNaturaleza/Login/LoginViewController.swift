//
//  AccessViewController.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Reyes Espino on 24/04/20.
//  Copyright © 2020 Marisol Reyes Espino. All rights reserved.
//

import UIKit
import PromiseKit
import Alamofire

class LoginViewController: UIViewController {
    
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var segueSwitch: UISwitch!

    @IBAction func loginButtonAction(_ sender: Any) {
        getUserInfo()
        .done { json -> Void in
            print(json.id!)
            print(json.token!)
            
            if json.id == 4 && json.token == "QpwL5tke4Pnpja7X4" {
                if self.segueSwitch.isOn {
                    UserDefaults.standard.set(true, forKey: "loginKey")
                } else {
                    UserDefaults.standard.set(false, forKey: "loginKey")
                }
                self.performSegue(withIdentifier: "colorSegue", sender: nil)
            }
        }
        .catch { error in
            let alert = UIAlertController(title: "Mensaje", message: "Datos invalidos", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
            self.present(alert, animated: true)
            print(error.localizedDescription)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    func getUserInfo() -> Promise<LoginService> {
        return Promise { seal in
            
            guard let email = emailTextField.text, !email.isEmpty else {
                let alert = UIAlertController(title: "Mensaje", message: "Debes proporcionar un correo", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
                    self.present(alert, animated: true)
                return
            }
            
            guard let password = passwordTextField.text, !password.isEmpty else {
                let alert = UIAlertController(title: "Mensaje", message: "Debes proporcionar una contraseña", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
                    self.present(alert, animated: true)
                return
            }
            
            if email != "" && password != "" {
                let login = LoginStruct(email: email, password: password)
                AF.request("https://reqres.in/api/register", method: .post, parameters: login)
                    .validate()
                    .responseJSON { response in
                        switch response.result {
                        case .success(let json):
                            guard let json = json  as? [String: Any] else {
                            return seal.reject(AFError.responseValidationFailed(reason: .dataFileNil))
                            }
                            let login = LoginService(JSON: json)
                            seal.fulfill(login!)
                            print(login!)
                        case .failure(let error):
                            seal.reject(error)
                        }
                    }
                }
            }
        }
    }
