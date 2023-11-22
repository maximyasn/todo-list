# todo-list
Crud application with spring mvc &amp; hibernate 

## Set up

Сonfigure the application launch:
1. Set hibernate properties;
2. Set MySQL password for root user;
3. Rebuild app with maven:
    ```
    mvn clean install
    ```
4. Run sql-script via Workbench;
5. Run *docker-compose.yaml*
    ```
   docker compose up
   ```
6. Browse http://localhost:8080/todo-list/tasks