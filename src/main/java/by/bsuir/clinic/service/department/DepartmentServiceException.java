package by.bsuir.clinic.service.department;

public class DepartmentServiceException extends RuntimeException {
    public DepartmentServiceException() {
    }

    public DepartmentServiceException(String message) {
        super(message);
    }

    public DepartmentServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentServiceException(Throwable cause) {
        super(cause);
    }
}
