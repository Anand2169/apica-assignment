## **API Documentation**  

### **User Service Endpoints**  
| Endpoint  | Method | Description | Access |
|----------|--------|-------------|--------|
| `/api/users` | POST | Register a new user | Public |
| `/api/users` | GET | Get all user details | ADMIN |
| `/api/users/{id}` | PUT | Update user | ADMIN |
| `/api/users/{id}` | DELETE | Delete user | ADMIN |

**Example Request (Register User):**  
```bash
curl -X POST -H "Content-Type: application/json" -d '{
  "username": "testuser",
  "password": "password123",
  "roles": ["USER"]
}' http://localhost:8080/api/users/register
```

### **Journal Service Endpoints**  
| Endpoint | Method | Description | Access |
|----------|--------|-------------|--------|
| `/api/events` | GET | Fetch all journaled events | ADMIN |

**Example Request (Get Events - Admin Auth Required):**  
```bash
curl -u admin:admin http://localhost:8081/api/events
```

---

## **Security**  
- **Default Admin Credentials**: `admin:admin` (configured in `application.properties`).  
- **Role-Based Access**:  
  - `ADMIN` → Full access.  
  - `USER` → Limited access (e.g., self-profile updates).  

---

## **Testing**  
### **Unit Tests**  
Run tests for each service:  
```bash
cd user-service && mvn test
cd journal-service && mvn test
```

### **Integration Testing**  
1. Register a user.  
2. Check if the event appears in the Journal Service:  
   ```bash
   curl -u admin:admin http://localhost:8081/api/events
   ```

---

## **Troubleshooting**  
- **Kafka Not Connecting?**  
  Ensure Kafka and Zookeeper are running (`docker-compose ps`).  
- **Database Issues?**  
  Verify MySQL is up and credentials match `application.properties`.  

---

## **Future Improvements**  
- Add Swagger/OpenAPI documentation.  
- Implement JWT-based authentication.  
- Add more unit/integration tests.  

---

## **License**  
MIT License.  
