# shopsfinder
Uses Google Places API to show nearby shops (within 500 metres) on the map

Some comments:
- API-key is not included in here, you should put it in 'local.properties':

MAPS_API_KEY=<YOUR_API_KEY>
- For interctive Info Window within MapView additional library was used:

https://github.com/Appolica/InteractiveInfoWindowAndroid

- There is no checks on connection state (and other kinds of errors' checks), it should be done for production stage :). So it's just an example how this app could be done
