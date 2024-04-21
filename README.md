# Todo-Application

## SetUp & Run:

### DB SetUp:

- Install my-sql-installer from <https://dev.mysql.com/downloads/installer/>
- Go to Services in your system and search for MYSQL Server
- Right click and select “start”.
- Install “MYSQL Workbench” for easier data manipulation.
- Open “MYSQL Workbench”, click “+” near MYSQL Connections.
- Enter the Connection Name and click ok to connect.
- Now click the connection.
- Enter the SQL Queries.
  - Create a database: create database todoDB;
  - Use to utilize the database: use todoDB;
  - Create table named Project : 
    - CREATE TABLE Project (
    - `    `p\_id BIGINT AUTO\_INCREMENT PRIMARY KEY,
    - `    `title VARCHAR(255) NOT NULL,
    - `    `created\_date VARCHAR(255) NOT NULL
    - );
  - Create table named todo : 
    - CREATE TABLE Todo (
    - `    `t\_id BIGINT AUTO\_INCREMENT PRIMARY KEY,
    - `    `description TEXT NOT NULL,
    - `    `status VARCHAR(20) NOT NULL,
    - `    `created\_date VARCHAR(20) NOT NULL,
    - `    `updated\_date VARCHAR(20) NOT NULL,
    - `    `p\_id BIGINT,
    - `    `FOREIGN KEY (p\_id) REFERENCES Project(p\_id)
    - );
  - Insert some values to the tables, if initially need to view projects are corresponding todo’s
    - insert into project values(1,"Project1","18-4-2024");
    - insert into project values(2,"Project2","18-4-2024");
    - insert into project values(3,"Project3","18-4-2024");
    - insert into project values(4,"Project3","18-4-2024");

    - insert into todo values(1,"Todo1","pending","18-4-2024","18-4-2024",1);
    - insert into todo values(2,"Todo2","pending","18-4-2024","18-4-2024",1);
    - insert into todo values(3,"Todo3","pending","18-4-2024","18-4-2024",1);
    - insert into todo values(4,"Todo4","pending","18-4-2024","18-4-2024",2);
- Now the DB with necessary tables are created.




### Back-End Server SetUp (For Eclipse):

- Open “Eclipse for Java Enterprise and Web Developers”.
- File->Import->Maven->Existing Maven Project->Browse-> ”Choose the project folder(ToDoApp)”
- Right click on the project and select “Run As-> Maven clean” & “Run As-> Maven install” to download the dependencies.
- Open the Project structure: “ToDoApp\src\main\java\com\todo\ToDoApp\ToDoAppApplication.java
- Right click on the file “ToDoAppApplication.java” and select “Run As Java Application”.
- Now you can see the application has started and running at port 8080.![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/3dcd8c8e-39ed-4540-a838-fb2563ae9c08)


### Add a Browser Extension :

- Install an extension “Allow CORS” to make use of both frontend and backend be deployed in same location (<http://localhost..>) without CORS error.
- Now open “Allow CORS” from extensions.
- Click on the image on left side “BIG C logo” to start the service.![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/88fa82f1-81c0-429b-aba4-b0a9475b7719)


### Front-End Server SetUp: (If Nodejs is installed and environment variables are set to path variable, then skip to the 5th point)

- Download Nodejs from <https://nodejs.org/en>
- Install the Nodejs exe file.
- Now add the node path to the path field under System variables.
- ![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/da87fb07-3542-4d64-898b-8a3214411196)

- Now, Locate to frontend application’s folder ”../todo”
- Right-click and “open in terminal”
- Give cmd “npm i .” to download the dependencies.
- Now give cmd as “npm start”
- Now the application launches in the web browser. 
![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/f5dffed4-b9af-4885-b7c1-01f07ff5c003)




## Test Instructions (Application Flow): 

- Login page for user validation. ![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/9a18ba9e-2828-4958-b90b-fbef10f684dc)

- Enter Username as “admin”
- Enter Password as “admin”
- Click Login
- Now the username and password are verified from server-end using stored hash values.
- And displays with User salutation, Projects created and Logout, where the userdata and loggedIn status is stored in session variable for realtime scenarios.![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/94ee43ac-32f7-4a17-9b34-7c8f467dfe84)

  - Add Project: Clicking this button allows you to enter the “Project Title” for the new Project, upon clicking “ok” add the new project. ![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/665507a6-8d9e-4095-a814-1bb95cdb9b3a)

  - Edit: Clicking this button allows you to edit the “Project Title” for the selected project. ![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/e4681ebc-ed6c-4e67-b267-14fb10c3d114)

  - X: Clicking this button allows you to remove the selected project. ![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/11e75fbf-3e78-4610-b354-b8cefded3fc9)

  - Now clicking on any project “Project 1” displays the corresponding TODO’s with Description, created date, updated date and completion status for the selected project.  (updated date is “not yet updated” showing no changes has occurred yet)![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/443c65a7-d368-4b16-ad12-f36c3dd699f1)

  - Checking a checkbox for a todo makes the status change from “pending” to “completed” and updating updated date. ![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/8599385b-6dd6-45ea-a598-41b1c21e3cf0)

  - Edit: Clicking this button allows you to edit the “ToDo description” for the selected todo. ![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/660e2d49-8708-45c7-b7ee-a76e78b088e1)

  - Remove: Clicking this button allows you to remove the selected todo. ![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/48ed2753-975d-451e-8400-e0da3f1ca2aa)

  - Add ToDo: Clicking this button allows you to enter the “todo description” for the new todo, upon clicking “ok” adds the new todo as pending and updated date “not yet updated”. ![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/e9c8447c-48a6-4d40-8b81-7d0ab469e769)

  - Export as Gist: Clicking this button allows you to create a gist as template specified and store to my gist section in my github and retrieve the gist and store it to my local system. ![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/f5c63b11-d7fe-4ab5-b1a7-c2e3bff4e824)

  - Local File download as .md ![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/1170bd0d-5942-46b3-86ca-0df0f5c21ecb)

  - Clicking on “Back” button redirects you back to “Project Page” due to session storage.  (Even on reload it’s just refreshes without loosing loggedIn status)![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/078b4fe5-c0fb-42a9-841a-fdd80bbb0616)

  - Logout: Clicking on this button logs out the user to login page along with removing the session details. ![image](https://github.com/Mahesh-Sivashankaran/Todo-Application/assets/53386098/bc3071dd-99bd-4ecb-9126-b9dc48e44428)



