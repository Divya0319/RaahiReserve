select b.bus_timing, b.company_name
from booking bo
         join bus_route br on br.bus_route_id = bo.bus_route_id
         join bus b on br.bus_id = b.bus_id
where bo.booking_id = 9;