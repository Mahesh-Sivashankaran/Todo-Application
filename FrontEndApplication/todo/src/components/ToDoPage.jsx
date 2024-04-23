import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Octokit } from "@octokit/core";
import { saveAs } from "file-saver"; // Import saveAs from FileSaver.js
import "../styles/ToDoPage.css";

const octokit = new Octokit({
  auth: "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",
}); // Replace 'YOUR_GITHUB_TOKEN' with your GitHub token

const ToDoPage = () => {
  const navigate = useNavigate();
  const { projectId } = useParams(); // Extract projectId from URL params
  const { projectTitle } = useParams(); // Extract projectTitle from URL params
  const [todos, setTodos] = useState([]);

  useEffect(() => {
    // Fetch todos based on projectId when component mounts
    fetch(`http://localhost:8080/ToDo/getToDos?proId=${projectId}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to fetch todos");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Fetched todos:", data); // Debugging
        setTodos(data.todos);
      })
      .catch((error) => console.error("Error fetching todos:", error));
  }, [projectId]); // Dependency on projectId

  const handleDeleteTodo = (todoId) => {
    fetch(`http://localhost:8080/ToDo/deleteToDo/${todoId}`, {
      method: "DELETE",
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to delete todo");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Deleted todo:", data); // Debugging
        setTodos(data.todos);
      })
      .catch((error) => console.error("Error deleting todo:", error));
  };

  const handleEditDescription = (todoId, newDescription) => {
    fetch("http://localhost:8080/ToDo/updateToDo", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        toId: todoId,
        description: newDescription,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to update todo description");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Updated todo:", data); // Debugging
        setTodos(data.todos);
      })
      .catch((error) =>
        console.error("Error updating todo description:", error)
      );
  };

  const handleStatusChange = (todoId, status) => {
    fetch(`http://localhost:8080/ToDo/changeStatus`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json", // Specify the content type as JSON
      },
      body: JSON.stringify({ toId: todoId, status: status }), // Stringify the data object
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to change todo status");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Changed todo status:", data); // Debugging
        setTodos(data.todos);
      })
      .catch((error) => console.error("Error changing todo status:", error));
  };

  const exportAsGist = async () => {
    const markdownContent = await generateMarkdown();
    // Save the content to local system using FileSaver.js
    const blob = new Blob([markdownContent], { type: "text/markdown" });
    saveAs(blob, "ProjectSummary.md");
  };

  const generateMarkdown = () => {
    const completedTodos = todos.filter((todo) => todo.status === "completed");
    const pendingTodos = todos.filter((todo) => todo.status === "pending");

    const markdownContent = `
# ${projectTitle}

## Summary
${completedTodos.length} / ${todos.length} completed.

## Pending Todos
${pendingTodos.map((todo) => `- [ ] ${todo.description}`).join("\n")}

## Completed Todos
${completedTodos.map((todo) => `- [X] ${todo.description}`).join("\n")}`;

    return markdownContent;
  };

  // Function to add a new ToDo
  const addToDo = (description) => {
    const newToDo = {
      proId: projectId,
      description: description,
      status: "pending", // The initial status is "Pending"
      createdDate: new Date().toISOString().split("T")[0], // Today's date
      updatedDate: "Not yet Updated", // Not yet Updated
    };

    fetch(`http://localhost:8080/ToDo/addToDo`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newToDo),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to add ToDo");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Added ToDo:", data); // Debugging
        setTodos(data.todos);
      })
      .catch((error) => console.error("Error adding ToDo:", error));
  };

  return (
    <div className="project-container">
      <h2>TODOs List</h2>
      <ul>
        {todos.map((todo) => (
          <li key={todo.toId}>
            <div>
              <input
                style={{ transform: "scale(1.5)" }}
                type="checkbox"
                checked={todo.status === "completed"}
                onChange={(e) =>
                  handleStatusChange(
                    todo.toId,
                    e.target.checked ? "completed" : "pending"
                  )
                }
              />
              <span>{todo.description}</span>
              <span className="created-date">
                {" "}
                Created Date:- {todo.createdDate}
              </span>
              <span className="created-date">
                {" "}
                Updated Date:- <br></br>
                {todo.updatedDate}
              </span>

              <button
                onClick={() => {
                  const newDescription = prompt("Enter new description:");
                  if (newDescription) {
                    handleEditDescription(todo.toId, newDescription);
                  }
                }}
              >
                Edit
              </button>
              <button onClick={() => handleDeleteTodo(todo.toId)}>
                Remove
              </button>
            </div>
          </li>
        ))}
      </ul>
      <button
        className="add-todo-btn"
        onClick={() => {
          const description = prompt("Enter ToDo Description:");
          if (description) {
            addToDo(description);
          }
        }}
      >
        Add ToDo
      </button>{" "}
      <button
        className="add-todo-btn"
        onClick={() => {
          navigate("/");
        }}
      >
        Back
      </button>{" "}
      <button
        className="add-todo-btn"
        onClick={() => {
          exportAsGist();
        }}
      >
        Export as Gist
      </button>{" "}
    </div>
  );
};

export default ToDoPage;
