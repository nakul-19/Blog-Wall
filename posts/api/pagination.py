from rest_framework.pagination import (
    PageNumberPagination,
    )



class PostPageNumberPagination(PageNumberPagination):
    page_size = 20
