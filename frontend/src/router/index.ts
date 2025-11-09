import {createRouter, createWebHistory} from 'vue-router'
import {tokenService} from "@/services/tokenService.ts";

import RegisterView from "../views/RegisterView.vue";
import LoginView from "../views/LoginView.vue";
import HomeView from "../views/HomeView.vue";
import MyFavoriteBooksManagementView from "../views/MyFavoriteBooksManagementView.vue";
import AuthorManagementView from "../views/AuthorsManagementView.vue";
import AddAuthorView from "../views/AddAuthorView.vue";
import AuthorDetailsView from "../views/AuthorDetailsView.vue";
import AddBookView from "../views/AddBookView.vue";
import BookDetailsView from "../views/BookDetailsView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/register',
            name: 'register',
            component: RegisterView
        },
        {
            path: '/login',
            name: 'login',
            component: LoginView
        },
        {
            path: '/books',
            name: 'books',
            component: HomeView,
            meta: {requiresAuth: true}
        },
        {
          path: '/favorite-books',
          name: 'favorite-books',
          component: MyFavoriteBooksManagementView,
          meta: {requiresAuth: true}
        },
        {
            path: '/authors',
            name: 'authors',
            component: AuthorManagementView,
            meta: {requiresAuth: true}
        },
        {
            path: '/add-author',
            name: 'add-author',
            component: AddAuthorView,
            meta: {requiresAuth: true}
        },
        {
            path: '/authors/:id',
            name: 'author-details',
            component: AuthorDetailsView,
            meta: {requiresAuth: true}
        },
        {
            path: '/add-book',
            name: 'add-book',
            component: AddBookView,
            meta: {requiresAuth: true}
        },
        {
            path: '/books/:id',
            name: 'book-details',
            component: BookDetailsView,
            meta: {requiresAuth: true}
        },
        {
            path: '/',
            redirect: '/books'
        }
    ]
})

router.beforeEach((to, _from, next) => {
    const requiresAuth = to.meta.requiresAuth
    const hasToken = !!tokenService.getAccessToken()

    if (requiresAuth && !hasToken) {
        next({name: 'login'})
    } else {
        next()
    }
})

export default router
