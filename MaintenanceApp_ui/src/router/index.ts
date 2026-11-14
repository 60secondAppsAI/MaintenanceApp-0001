import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import MaintenancePlans from '../components/MaintenancePlans.vue';
import MaintenancePlanDetail from '../components/MaintenancePlanDetail.vue';
import MaintenanceTasks from '../components/MaintenanceTasks.vue';
import MaintenanceTaskDetail from '../components/MaintenanceTaskDetail.vue';
import MaintenanceRecords from '../components/MaintenanceRecords.vue';
import MaintenanceRecordDetail from '../components/MaintenanceRecordDetail.vue';
import MaintenanceChecklists from '../components/MaintenanceChecklists.vue';
import MaintenanceChecklistDetail from '../components/MaintenanceChecklistDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/maintenancePlans',
    name: 'MaintenancePlans',
    component: MaintenancePlans
  },
  {
    path: '/maintenancePlan/:id',
    name: 'MaintenancePlanDetail',
    component: MaintenancePlanDetail,
    props: true
  },
  {
    path: '/maintenanceTasks',
    name: 'MaintenanceTasks',
    component: MaintenanceTasks
  },
  {
    path: '/maintenanceTask/:id',
    name: 'MaintenanceTaskDetail',
    component: MaintenanceTaskDetail,
    props: true
  },
  {
    path: '/maintenanceRecords',
    name: 'MaintenanceRecords',
    component: MaintenanceRecords
  },
  {
    path: '/maintenanceRecord/:id',
    name: 'MaintenanceRecordDetail',
    component: MaintenanceRecordDetail,
    props: true
  },
  {
    path: '/maintenanceChecklists',
    name: 'MaintenanceChecklists',
    component: MaintenanceChecklists
  },
  {
    path: '/maintenanceChecklist/:id',
    name: 'MaintenanceChecklistDetail',
    component: MaintenanceChecklistDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
