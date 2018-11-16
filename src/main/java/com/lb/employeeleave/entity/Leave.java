package com.lb.employeeleave.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lb.employeeleave.dto.LeaveReportDTO;
import com.lb.employeeleave.util.enums.LeaveStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "leave_request")

@NamedQuery(name = "Leave.findLeaveByDate",
        query = "SELECT l FROM Leave l WHERE ((l.fromDate BETWEEN ?1 AND ?2) OR (l.toDate BETWEEN ?1 AND ?2)) " +
                "AND l.status='APPROVED' ")

@NamedNativeQuery(
        name = "Leave.generateLeaveReport",
        query =
                "select count(leave_id) as count, lt.type_name AS leaveType, month(lr.from_date) as month, lr.status as status\n" +
                        "from leave_request lr \n" +
                        "    INNER JOIN employee e ON e.employee_id = lr.employee_id\n" +
                        "\tINNER JOIN leave_type lt ON lt.leave_type_id = lr.leave_type\n" +
                        "    where \n" +
                        "    lr.from_date >= date_add(curdate(),INTERVAL -1 YEAR) OR\n" +
                        "    lr.to_date >= date_add(curdate(), INTERVAL -1 YEAR)\n" +
                        "    group by lt.type_name, month(lr.from_date), lr.status\n" +
                        "    order by month(lr.from_date)",
        resultSetMapping = "LeaveReportDTO"
)
@SqlResultSetMapping(
        name = "LeaveReportDTO",
        classes = @ConstructorResult(
                targetClass = LeaveReportDTO.class,
                columns = {
                        @ColumnResult(name = "count", type = Integer.class),
                        @ColumnResult(name = "leaveType", type = String.class),
                        @ColumnResult(name = "month", type = Integer.class),
                        @ColumnResult(name = "status", type = String.class)
                }
        )
)
public class Leave {

    @Id
    @Column(name = "leave_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "leave_type", nullable = false)
    private LeaveType leaveType;

    @Column(name = "leave_reason", columnDefinition = "TEXT", nullable = false)
    private String leaveReason;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

//    @Column(name = "approved")
//    private boolean approved;

    @Column(name = "denied_reason", columnDefinition = "TEXT")
    private String deniedReason;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private Employee reviewedBy;

    public Long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Long leaveId) {
        this.leaveId = leaveId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getDeniedReason() {
        return deniedReason;
    }

    public void setDeniedReason(String deniedReason) {
        this.deniedReason = deniedReason;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Employee getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(Employee reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leave leave = (Leave) o;
        return
                Objects.equals(leaveId, leave.leaveId) &&
                Objects.equals(employee, leave.employee) &&
                Objects.equals(leaveType, leave.leaveType) &&
                Objects.equals(leaveReason, leave.leaveReason) &&
                Objects.equals(fromDate, leave.fromDate) &&
                Objects.equals(toDate, leave.toDate) &&
                Objects.equals(deniedReason, leave.deniedReason) &&
                status == leave.status &&
                Objects.equals(createdAt, leave.createdAt) &&
                Objects.equals(reviewedBy, leave.reviewedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leaveId, employee, leaveType, leaveReason, fromDate, toDate, deniedReason, status, createdAt, reviewedBy);
    }
}
