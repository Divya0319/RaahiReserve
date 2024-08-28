SELECT b.* FROM bus b
                    JOIN bus_route br
                         ON b.bus_id = br.bus_id
                    JOIN route r
                         ON br.route_id = r.route_id
WHERE r.source = 'Chhattisgarh'
  AND r.destination = 'Mumbai'
  AND b.bus_type = 'AC'
  AND b.available_seats > 0;