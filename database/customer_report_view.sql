CREATE VIEW customer_report_view AS
SELECT c.id         AS customer_id,
       c.first_name AS first_name,
       c.last_name  AS last_name
FROM customer c;