import React, { useState, useEffect } from "react";
import "../styles/LoginPage.css";

import ProjectPage from "./ProjectPage";

const LoginPage = () => {
  // State variables to store username and password
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    const storedUsername = sessionStorage.getItem("username");
    const storedLoggedIn = sessionStorage.getItem("loggedIn") === "true";

    if (storedUsername && storedLoggedIn) {
      setUsername(storedUsername);
      setLoggedIn(true);
    }
  }, []);

  // Function to handle form submission
  const handleSubmit = (event) => {
    event.preventDefault(); // Prevent default form submission behavior

    // Check if username and password are valid
    fetch(
      "http://localhost:8080/User/validUser?user=" +
        username +
        "&pass=" +
        password
    )
      .then((response) => response.json())
      .then((userStatus) => {
        if (userStatus.status === "Valid") {
          // If valid, set loggedIn state to true
          setLoggedIn(true);
          sessionStorage.setItem("loggedIn", "true");
          sessionStorage.setItem("username", username);
          // Here you can perform further actions like sending login data to a server
          console.log("Login successful!");
        } else {
          // If not valid, alert the user
          alert("Invalid User Credentials!");
        }
      })
      .catch((error) => console.error("Error fetching data:", error));
  };

  return (
    <div className="login-container">
      {" "}
      {/* Apply CSS class to the container */}
      {loggedIn ? (
        // If logged in, show a welcome message
        <div>
          <h2>Welcome, {sessionStorage.getItem("username")}!</h2>
          <ProjectPage />
          <button
            onClick={() => {
              setLoggedIn(false);
              sessionStorage.removeItem("loggedIn");
              sessionStorage.removeItem("username");
              setUsername("");
              setPassword("");
            }}
          >
            Logout
          </button>
        </div>
      ) : (
        // If not logged in, show the login form
        <form onSubmit={handleSubmit}>
          <h2>Login</h2>
          <label>
            Username:
            <input
              type="text"
              placeholder="Enter the username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </label>
          <br />
          <label>
            Password:
            <input
              type="password"
              placeholder="Enter the password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </label>
          <br />
          <button type="submit">Login</button>
        </form>
      )}
    </div>
  );
};

export default LoginPage;
