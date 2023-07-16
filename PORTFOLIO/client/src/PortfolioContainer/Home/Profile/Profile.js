import React from "react"
import Typical from "react-typical"
import "./Profile.css"

export default function Profile() {
  return (
    <div className="profile-container">
      <div className="profile-parent">
        <div className="profile-details">
          <div className="colz">
            <div className="colz-icon">
              <a href="https://www.instagram.com/">
                <i className="fa fa-instagram"></i>
              </a>
              <a href="https://github.com/KeshavSaraogi">
                <i className="fa fa-github-square"></i>
              </a>
              <a href="https://www.linkedin.com/in/keshav-saraogi/">
                <i className="fa fa-linkedin-square"></i>
              </a>
            </div>
          </div>
          <div className="profile-details-name">
            <span className="primary-text">
              {""}
              HELLO, I AM{" "}
              <span className="highlighted-text">KESHAV SARAOGI</span>
            </span>
          </div>
          <div className="profile-details-role">
            <span className="primary-text">
              {""}
              <h1>{""}</h1>
              <span className="profile-role-tagline">
                Building Full-Stack Web-Apps and Android Apps
              </span>
            </span>
          </div>
          <div className="profile-options">
            <button className="btn primary-btn">
              {""}
              Hire Me{" "}
            </button>
            <a href="resume.pdf" downlaod="keshav-saraogi-resume.pdf">
              <button className="btn highlighted-btn">GET RESUME</button>
            </a>
          </div>
        </div>
        <div className="profile-picture">
          <div className="profile-picture-background"></div>
        </div>
      </div>
    </div>
  )
}
