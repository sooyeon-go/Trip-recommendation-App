import sys
import warnings
warnings.simplefilter(action='ignore', category=FutureWarning)
import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.metrics import mean_squared_error

def def_final(*d):
    data = pd.read_csv('data_fin_.csv',encoding = 'utf-8')
    user = pd.read_csv('user_data.csv',encoding = 'utf-8')
    for i in d:
        user['rating'] = int(i);
    #user['rating'] = [d1, d2, d3, d4, d5, d6, d7, d8]
    new = data[['userid','courseid','rating']]
    user_new = user[['userid','courseid','rating']]
    new = new.append(user_new)
    data_matrix = new.pivot_table('rating', index = 'userid', columns = 'courseid')
    data_matrix = data[['userid','course','rating']]
    user_matrix = user[['userid','course','rating']]
    data_matrix = data_matrix.append(user_matrix)
    rating_matrix = data_matrix.pivot_table('rating', index = 'userid', columns = 'course')
    rating_matrix = rating_matrix.fillna(0)
    rating_matrix_T = rating_matrix.transpose()
    item_sim = cosine_similarity(rating_matrix_T, rating_matrix_T)
    item_sim_df = pd.DataFrame(data = item_sim, index = rating_matrix.columns, columns = rating_matrix.columns)
    def predict_rating(rating_arr, item_sim_arr):
        rating_pred = rating_arr.dot(item_sim_arr) / np.array([np.abs(item_sim_arr).sum(axis = 1)])
        return rating_pred
    rating_pred = predict_rating(rating_matrix.values, item_sim_df.values)
    rating_pred_matrix = pd.DataFrame(data = rating_pred, index = rating_matrix.index, columns = rating_matrix.columns)
    def get_mse(pred, actual):
        pred = pred[actual.nonzero()].flatten() 
        actual = actual[actual.nonzero()].flatten()
        return mean_squared_error(pred, actual)
    def predict_rating_topsim(rating_arr, item_sim_arr, n = 20):
        pred = np.zeros(rating_arr.shape) 
    
        for col in range(rating_arr.shape[1]): 
            top_n_items = [np.argsort(item_sim_arr[:, col])[: -n-1 : -1]] 
            for row in range(rating_arr.shape[0]): 
                pred[row, col] = item_sim_arr[col, :][top_n_items].dot(rating_arr[row, :][top_n_items].T)
                pred[row, col] /= np.sum(np.abs(item_sim_arr[col, :][top_n_items]))
        return pred
    rating_pred = predict_rating_topsim(rating_matrix.values, item_sim_df.values, n = 20)
    rating_pred_matrix = pd.DataFrame(data = rating_pred, index = rating_matrix.index, columns = rating_matrix.columns)
    user_rating_id = rating_matrix.loc[8, :]
    def get_unseen_course1(rating_matrix, userid):
        user_rating = rating_matrix.loc[userid, :] 
        already_go = user_rating[ user_rating > 0 ].index.tolist()
        course_list = rating_matrix.columns.tolist()
        un_list = [ course for course in course_list if course not in already_go ]
        return un_list
    def recomm_course_by_userid1(pred_df, userid, un_list, top_n):
        recomm_course = pred_df.loc[userid, un_list].sort_values(ascending = False)[: top_n]
        return recomm_course
    un_list = get_unseen_course1(rating_matrix, 8)
    recomm_course = recomm_course_by_userid1(rating_pred_matrix, 8, un_list, 30)
    recomm_course = pd.DataFrame(data = recomm_course.values, index = recomm_course.index, columns = ['pred_score'])
    def get_rmse(R, P, Q, non_zeros):
        error = 0
        full_pred_matrix = np.dot(P, Q.T) 
        x_non_zero_ind = [non_zero[0] for non_zero in non_zeros]
        y_non_zero_ind = [non_zero[1] for non_zero in non_zeros]
        R_non_zeros = R[x_non_zero_ind, y_non_zero_ind]
        full_pred_matrix_non_zeros = full_pred_matrix[x_non_zero_ind, y_non_zero_ind]
        mse = mean_squared_error(R_non_zeros, full_pred_matrix_non_zeros)
        rmse = np.sqrt(mse)
        return rmse
    def matrix_factorization(R, K, steps, learning_rate, r_lambda):
        num_users, num_items = R.shape
        np.random.seed(1)
        P = np.random.normal(scale = 1./K, size = (num_users, K))
        Q = np.random.normal(scale = 1./K, size = (num_items, K))
    
        prev_rmse = 10000
        break_count = 0
    
        non_zeros = [ (i, j, R[i,j]) for i in range(num_users) for j in range(num_items) if R[i, j] > 0]
     
        for step in range(steps):
            for i, j, r in non_zeros:
                eij = r - np.dot(P[i, :], Q[j, :].T)
                P[i, :] = P[i, :] + learning_rate * (eij * Q[j, :] - r_lambda * P[i, :])
                Q[j, :] = Q[j, :] + learning_rate * (eij * P[i, :] - r_lambda * Q[j, :])
            rmse = get_rmse(R, P, Q, non_zeros)
            #if(step % 10) == 0:
                #print("### iteration step : ",step, " rmse : ", rmse)
        return P,Q
    rating = new[['userid', 'courseid', 'rating']]
    rating_matrix = rating.pivot_table('rating', index = 'userid', columns = 'courseid')
    rating_matrix = data_matrix.pivot_table('rating', index = 'userid', columns = 'course')
    P, Q = matrix_factorization(rating_matrix.values, 50, 100, 0.01, 0.01)
    pred_matrix = np.dot(P, Q.T)
    rating_pred_matrix = pd.DataFrame(data = pred_matrix, index = rating_matrix.index, columns = rating_matrix.columns)
    def get_unseen_course2(rating_matrix, userid):
        user_rating = rating_matrix.loc[userid, :]
        already_go = user_rating[ user_rating > 0 ].index.tolist()
        course_list = rating_matrix.columns.tolist()
        un_list = [ course for course in course_list if course not in already_go ]
        return un_list
    def recomm_course_by_userid2(pred_df, userid, un_list, top_n):
        recomm_course = pred_df.loc[userid, un_list].sort_values(ascending = False)[: top_n]
        return recomm_course
    un_list = get_unseen_course2(rating_matrix, 8)
    matrix_recomm_course = recomm_course_by_userid2(rating_pred_matrix, 8, un_list, 30)
    matrix_recomm_course = pd.DataFrame(data = matrix_recomm_course.values, index = matrix_recomm_course.index, columns = ['pred_score'])
    course_df = data.drop(['userid', 'rating'], axis = 1)
    user_df = user[['course','rating']]
    inputId = course_df[course_df['course'].isin(user_df['course'].tolist())]
    result = pd.merge(inputId, user_df)
    result_df = result[['courseid', 'course', 'rating']]
    result_detail = result[['specific', 'water', 'tree', 'ruins', 'eat', 'hotel', 'cafe', 'pet', 'spring', 'summer', 'fall', 'winter']]
    userProfile = result_detail.transpose().dot(result_df['rating'])
    courseTable = course_df.set_index(course_df['courseid'])
    courseTable = courseTable[['specific', 'water', 'tree', 'ruins', 'eat', 'hotel', 'cafe', 'pet', 'spring', 'summer', 'fall', 'winter']]
    recoTable_df = ((courseTable * userProfile).sum(axis = 1)) / (userProfile.sum())
    recoTable_df = recoTable_df.sort_values(ascending = False)
    rank = course_df.loc[course_df['courseid'].isin(recoTable_df.head(20).keys())]
    rankId = data[data['course'].isin(rank['course'].tolist())]
    final = pd.merge(rankId, rank)
    fi_result = final[['courseid', 'course', 'rating']].sort_values('rating', ascending = False)
    fi_result = fi_result.drop_duplicates('courseid', keep='first')
    fi_result = fi_result.reset_index(drop = True)
    fi_result.course.to_csv("content_based.csv",mode="w",header=False)
    recomm_course.to_csv("item_based.csv",mode="w")
    matrix_recomm_course.to_csv("matrix_factorization.csv",mode="w")
    item_based = pd.read_csv('item_based.csv', encoding = 'utf-8')
    matrix_factorization = pd.read_csv('matrix_factorization.csv', encoding = 'utf-8')
    dupli = item_based[item_based['course'].isin(matrix_factorization['course'].tolist())]
    dupli = dupli.reset_index(drop = True)
    dupli.course.to_csv("dupli.csv",mode="w",header=False)
    return dupli, fi_result

dupli, fi_result = def_final(*(sys.argv[1],sys.argv[2],sys.argv[3],sys.argv[4],sys.argv[5],sys.argv[6],sys.argv[7],sys.argv[8]))
print(dupli.course)
print(fi_result.course)
