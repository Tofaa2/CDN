# CDN
A basic CDN server written using spring boot, supports uploading, downloading and multipart downloading to split up downloads for faster speeds. I've also added a basic upload file controller.

# Endpoints

- /uploader # the main uploader html site, image below
- /upload # params:  file (multipart file) upload something to the server
- /download # params:  file (string) download a file from the server
- /downloadMultipart # params: file (string) download a file from the server using multipart download.

# TODO
- [ ] Authentication
- [ ] Basic configuration
- [ ] SDK/API 


![plot](assets/uploader.png)