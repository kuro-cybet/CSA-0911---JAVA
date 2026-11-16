# Test Cases

| Test | Input / action | Expected result |
|---|---|---|
| Allocate first vehicle | Enter a valid vehicle | First available slot returned |
| Allocate until full | Enter more vehicles than capacity | Full-facility error |
| Duplicate registration | Enter same active registration twice | Second entry rejected |
| Release vehicle | Exit an active registration | Slot released |
| Unknown registration | Exit a registration without active ticket | No active ticket error |
| Zero-minute stay | Entry and exit at same time | Parking fee is zero, except flat-fee e-bike |
| 61-minute two-wheeler | 61 minutes | Rs 20 |
| 130-minute car | 130 minutes | Rs 90 |
| EV charging | 130 minutes plus 10 kWh | Rs 210 |
| E-bike | Any session | Flat fee Rs 25 |
| Delivery van peak period | Two hours during 9 AM–5 PM | Rs 120 |
| Delivery van off-peak | Two hours outside 9 AM–5 PM | Rs 60 |
| Unequal floor capacities | Configure `{2, 1, 4}` | All seven slots can be allocated |

Run the demo from the application menu to verify the basic vehicle and fee workflow.
