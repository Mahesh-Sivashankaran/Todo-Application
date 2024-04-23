import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/ProjectPage.css";

const ProjectPage = () => {
  const navigate = useNavigate();

  const [projects, setProjects] = useState([]);

  // Function to fetch all projects
  const fetchProjects = () => {
    fetch("http://localhost:8080/Project/getAllProjects")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to fetch projects");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Fetched projects:", data); // Debugging
        if (!Array.isArray(data.projects)) {
          throw new Error("Projects data is not an array");
        }
        setProjects(data.projects);
      })
      .catch((error) => console.error("Error fetching projects:", error));
  };

  // Function to delete a project
  const deleteProject = (projectId) => {
    console.log("Deleting project with ID:", projectId);
    fetch("http://localhost:8080/Project/deleteProject/" + projectId, {
      method: "DELETE",
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to delete project");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Deleted project:", data); // Debugging
        if (!Array.isArray(data.projects)) {
          throw new Error("Projects data is not an array");
        }
        setProjects(data.projects);
      })
      .catch((error) => console.error("Error deleting project:", error));
  };

  // Function to update project title
  const updateProjectTitle = (projectId, newTitle) => {
    fetch("http://localhost:8080/Project/updateProject", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        proId: projectId,
        title: newTitle,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to update project title");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Updated project:", data); // Debugging
        if (!Array.isArray(data.projects)) {
          throw new Error("Projects data is not an array");
        }
        setProjects(data.projects);
      })
      .catch((error) => console.error("Error updating project:", error));
  };

  // Function to add a new project
  const addProject = (proTitle) => {
    const newProject = {
      title: proTitle,
      createdDate: new Date().toISOString().split("T")[0], // Today's date
    };

    fetch("http://localhost:8080/Project/addProject", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newProject),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to add project");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Added project:", data); // Debugging
        setProjects(data.projects);
      })
      .catch((error) => console.error("Error adding project:", error));
  };

  // To get list of ToDo's
  const handleListItemClick = (proId, proTitle) => {
    // To pass proTitle to child component
    // Navigate to ToDoPage when list item is clicked
    navigate(`/todo/${proId}/${proTitle}`);
  };

  // useEffect to fetch projects when component mounts
  useEffect(() => {
    fetchProjects();
  }, []);

  return (
    <div className="project-container">
      <h2>Projects</h2>
      <ul>
        {projects.map((project) => (
          <li
            key={project.proId}
            onClick={() => handleListItemClick(project.proId, project.title)}
          >
            {console.log("project.proId: " + project.proId)}
            <div>
              <span className="project-name">{project.title}</span>
              <span className="created-date">
                {" "}
                Created Date:- {project.createdDate}
              </span>
            </div>
            <button
              onClick={(e) => {
                e.stopPropagation();
                deleteProject(project.proId);
              }}
            >
              X
            </button>
            <button
              onClick={(e) => {
                e.stopPropagation();
                const newTitle = prompt("Enter new title:");
                if (newTitle) {
                  updateProjectTitle(project.proId, newTitle);
                }
              }}
            >
              Edit
            </button>
          </li>
        ))}
      </ul>
      <button
        className="add-project-btn"
        onClick={() => {
          const title = prompt("Enter project title:");
          if (title) {
            addProject(title);
          }
        }}
      >
        Add Project
      </button>{" "}
      {/* Button to add a new project */}
    </div>
  );
};

export default ProjectPage;
