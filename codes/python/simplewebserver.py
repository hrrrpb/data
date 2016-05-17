#!/usr/local/bin/python3

import http.server as hs

class OurRequestHandler (hs.BaseHTTPRequestHandler):

    def do_GET(self):
        self.send_response(200)
        self.end_headers()
        source = "<h1>This is our web site.</h1>"
        self.wfile.write(bytes(source, "utf-8"))
        return

srv = hs.HTTPServer(("", 8080), OurRequestHandler)
srv.serve_forever()

