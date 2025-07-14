# CCSD Bids Java

A Spring Boot web application for managing construction bids and subcontractor listings for CCSD (Construction Company/School District).

## Features

- **Bid Management**: Create, view, edit, and list construction bids
- **Subcontractor Management**: Manage subcontractor listings and information
- **Authentication & Security**: Secure login system with Spring Security
- **Administrative Dashboard**: Admin interface for managing bids and subcontractors
- **Public Interface**: Public-facing pages for viewing available opportunities
- **File Management**: Upload and download PDF documents for bids and subcontractors

## Technology Stack

- **Java 21**
- **Spring Boot 3.4.4**
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database abstraction layer
- **Thymeleaf** - Server-side templating engine
- **MySQL** - Database
- **Bootstrap 5** - Frontend styling
- **HTMX** - Dynamic web interactions

## Project Structure

```
src/
├── main/
│   ├── java/com/bryansiegel/ccsdbidsjava/
│   │   ├── config/          # Security configuration
│   │   ├── controllers/     # Web controllers
│   │   ├── models/          # Entity models
│   │   ├── repositories/    # Data repositories
│   │   └── services/        # Business logic
│   └── resources/
│       ├── static/          # CSS, JS, images
│       └── templates/       # Thymeleaf templates
└── test/                    # Test files
```

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL 8.0+

## Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ccsd-bids-java
   ```

2. **Database Setup**
   - Create a MySQL database
   - Create `src/main/resources/env.properties` with:
     ```properties
     DB_DATABASE=your_database_name
     DB_USER=your_username
     DB_PASSWORD=your_password
     ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   Or on Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

4. **Access the application**
   - Open your browser to `http://localhost:8080`
   - Admin interface available at `/admin`
   - Default credentials: username=`user`, password=`password`

## Configuration

The application uses environment-based configuration. Create an `env.properties` file in `src/main/resources/` with:

```properties
DB_DATABASE=ccsd_bids
DB_USER=your_db_user
DB_PASSWORD=your_db_password
```

## Development

### Building the project
```bash
./mvnw clean package
```

### Running tests
```bash
./mvnw test
```

### Development mode
The application includes Spring Boot DevTools for hot reloading during development.

## API Endpoints

### Public Endpoints
- `GET /` - Public homepage
- `GET /login` - Login page

### Admin Endpoints (Authentication required)
- `GET /admin/dashboard/` - Admin dashboard
- `GET /admin/bids` - List all bids
- `GET /admin/bids/create` - Create new bid form
- `POST /admin/bids/create` - Submit new bid
- `GET /admin/bids/edit/{id}` - Edit bid form
- `POST /admin/bids/edit/{id}` - Update bid
- `GET /admin/bids/delete/{id}` - Delete bid
- `GET /admin/bids/{bidId}/view` - View bid details
- `GET /admin/bids/download/{id}` - Download bid advertisement PDF
- `GET /admin/bids/download/preBid/{id}` - Download pre-bid sign-in sheet PDF
- `GET /admin/bids/download/bidTab/{id}` - Download bid tabulation sheet PDF

### Subcontractor Endpoints
- `GET /admin/bids/{bidId}/subcontractors` - List subcontractors for a bid
- `GET /admin/bids/{bidId}/subcontractors/create` - Create subcontractor form
- `POST /admin/bids/{bidId}/subcontractors/create` - Submit new subcontractor
- `GET /admin/bids/{bidId}/subcontractors/edit/{id}` - Edit subcontractor form
- `POST /admin/bids/{bidId}/subcontractors/edit/{id}` - Update subcontractor
- `GET /admin/bids/{bidId}/subcontractors/delete/{id}` - Delete subcontractor
- `GET /admin/bids/{bidId}/subcontractors/download/{id}` - Download subcontractor document PDF

## Database Schema

### Bids Table
- `id` - Primary key
- `contract_name` - Name of the contract
- `mpid_number` - MPID identification number
- `advertisement_for_bids` - PDF document (BLOB)
- `advertisement_for_bids_url` - URL to external document
- `pre_bid_sign_in_sheet` - PDF document (BLOB)
- `pre_bid_sign_in_sheet_url` - URL to external document
- `bid_tabulation_sheet` - PDF document (BLOB)
- `bid_tabulation_sheet_url` - URL to external document
- `is_active` - Boolean flag for active status

### SubContractorListing Table
- `id` - Primary key
- `sub_contractor_company_name` - Company name
- `sub_contractor_document_id` - Document identifier
- `sub_contractor_document` - PDF document (BLOB)
- `sub_contractor_document_url` - URL to external document
- `bids_id` - Foreign key to Bids table

## Testing

The application includes comprehensive test coverage:

- **Unit Tests**: Service layer tests with mocked dependencies
- **Integration Tests**: Controller tests with Spring context
- **Repository Tests**: JPA repository tests with test database
- **Security Tests**: Authentication and authorization tests

Run tests with:
```bash
./mvnw test
```

## Security

- Uses Spring Security for authentication and authorization
- Admin endpoints require authentication
- CSRF protection enabled
- In-memory user store (development only)
- File upload size limits: 10MB per file

## License

This project is proprietary software developed for CCSD.