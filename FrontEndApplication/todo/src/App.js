import React from "react";
import { Routes, Route } from "react-router-dom";

import LoginPage from "./components/LoginPage";
import ToDoPage from "./components/ToDoPage";

const App = () => {
  return (
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/todo/:projectId/:projectTitle" element={<ToDoPage />} />
    </Routes>
  );
};

export default App;
