//
//  APIRequest.swift
//  iOS
//
//  Created by 정지승 on 2021/07/04.
//

import UIKit

class APIRequest {
    private let baseUrl: String = "http://118.67.131.182:8888"
    
    static let authSignInUrl: String = "/auth/social/login"
    
    static let userPointGetUrl: String = "/user/point"
    
    static let worryPostUrl: String = "/post"
    
    static let tagListGetUrl: String = "/tag"
    static let tagGetPatchUrl: String = "/tag/interest"
    
    static let votePostUrl: String = "/vote"
    
    func requestGet<T: Codable>(url: String, voType: T.Type, completionHandler: @escaping (Bool, Any) -> Void) {
        let urlString = "\(baseUrl)\(url)"
        let encodedString = urlString.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
        
        guard let url = URL(string: encodedString) else {
            print("Error: cannot create url")
            return
        }
        
        var request: URLRequest = URLRequest(url: url)
        request.httpMethod = "GET"
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else {
                print("Error: with \(error.debugDescription)")
                return
            }
            
            guard let data = data else {
                print("Error: Did not receive data")
                return
            }
            
            guard let response = response as? HTTPURLResponse else {
                print("Error: HTTP request failed")
                return
            }
            
            guard (200..<300) ~= response.statusCode else {
                print("Error: HTTP request failed code is \(response.statusCode)")
                return
            }
            
            print(response.debugDescription)
            let responseData = String(data: data, encoding: String.Encoding.utf8)
            print(responseData ?? "")
            
            guard let output = try? JSONDecoder().decode(T.self, from: data) else {
                print("Error: JSON Data Parsing error")
                return
            }
            
            completionHandler(true, output)
        }.resume()
    }
    
    // with body
    func requestPost<T: Codable>(url: String, method: String, voType: T.Type, param: [String: Any], completionHandler: @escaping (Bool, Any) -> Void) {
        let sendData = try! JSONSerialization.data(withJSONObject: param, options: [])
        let urlString = "\(baseUrl)\(url)"
        let encodedString = urlString.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
        
        guard let url = URL(string: encodedString) else {
            print("Error: cannot create url")
            return
        }
        
        var request: URLRequest = URLRequest(url: url)
        request.httpMethod = method
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpBody = sendData
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else {
                print("Error: with \(error!)")
                return
            }
            
            guard let data = data else {
                print("Error: Did not receive data")
                return
            }
            
            guard let response = response as? HTTPURLResponse else {
                print("Error: HTTP request failed")
                return
            }
            
            guard (200..<300) ~= response.statusCode else {
                print("Error: HTTP request failed code is \(response.statusCode)")
                return
            }
            
            print(response.debugDescription)
            let responseData = String(data: data, encoding: String.Encoding.utf8)
            print(responseData ?? "")
            

            guard let output = try? JSONDecoder().decode(voType, from: data) else {
                print("Error: JSON Data Parsing error")
                return
            }
            completionHandler(true, output)

            
        }.resume()
    }
    
    func requestGet(url: String, completionHandler: @escaping (Bool) -> Void) {
        let urlString = "\(baseUrl)\(url)"
        let encodedString = urlString.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
        
        guard let url = URL(string: encodedString) else {
            print("Error: cannot create url")
            return
        }
        
        var request: URLRequest = URLRequest(url: url)
        request.httpMethod = "GET"
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else {
                print("Error: with \(error.debugDescription)")
                return
            }

            guard let response = response as? HTTPURLResponse else {
                print("Error: HTTP request failed")
                return
            }
            
            guard (200..<300) ~= response.statusCode else {
                print("Error: HTTP request failed code is \(response.statusCode)")
                return
            }
            
            print(response.debugDescription)

            completionHandler(true)
        }.resume()
    }
    
    // with body
    func requestPost(url: String, method: String, param: [String: Any], completionHandler: @escaping (Bool) -> Void) {
        let sendData = try! JSONSerialization.data(withJSONObject: param, options: [])
        let urlString = "\(baseUrl)\(url)"
        let encodedString = urlString.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
        
        guard let url = URL(string: encodedString) else {
            print("Error: cannot create url")
            return
        }
        
        var request: URLRequest = URLRequest(url: url)
        request.httpMethod = method
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpBody = sendData
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            guard error == nil else {
                print("Error: with \(error!)")
                return
            }

            guard let response = response as? HTTPURLResponse else {
                print("Error: HTTP request failed")
                return
            }
            
            guard (200..<300) ~= response.statusCode else {
                print("Error: HTTP request failed code is \(response.statusCode)")
                return
            }
            
            print(response.debugDescription)

            completionHandler(true)
        }.resume()
    }
    
    func request<T: Codable>(url: String, method: String, voType: T.Type, param: [String: Any]? = nil, completionHandler: @escaping (Bool, Any) -> Void) {
        if method == "GET" {
            requestGet(url: url, voType: voType) { (success, data) in
                completionHandler(success, data)
            }
        } else {
            requestPost(url: url, method: method, voType: voType, param: param!) { (success, data) in
                completionHandler(success, data)
            }
        }
    }
    
    func request(url: String, method: String, param: [String: Any]? = nil, completionHandler: @escaping (Bool) -> Void) {
        if method == "GET" {
            requestGet(url: url) { (success) in
                completionHandler(success)
            }
        } else {
            requestPost(url: url, method: method, param: param!) { (success) in
                completionHandler(success)
            }
        }
    }
}
